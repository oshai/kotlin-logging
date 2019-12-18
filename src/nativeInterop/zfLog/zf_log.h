#pragma once

#ifndef _ZF_LOG_H_
#define _ZF_LOG_H_

/* To detect incompatible changes you can define ZF_LOG_VERSION_REQUIRED to be
 * the current value of ZF_LOG_VERSION before including this file (or via
 * compiler command line):
 *
 *   #define ZF_LOG_VERSION_REQUIRED 4
 *   #include <zf_log.h>
 *
 * Compilation will fail when included file has different version.
 */
#define ZF_LOG_VERSION 4
#if defined(ZF_LOG_VERSION_REQUIRED)
	#if ZF_LOG_VERSION_REQUIRED != ZF_LOG_VERSION
		#error different zf_log version required
	#endif
#endif

/* Log level guideline:
 * - ZF_LOG_FATAL - happened something impossible and absolutely unexpected.
 *   Process can't continue and must be terminated.
 *   Example: division by zero, unexpected modifications from other thread.
 * - ZF_LOG_ERROR - happened something possible, but highly unexpected. The
 *   process is able to recover and continue execution.
 *   Example: out of memory (could also be FATAL if not handled properly).
 * - ZF_LOG_WARN - happened something that *usually* should not happen and
 *   significantly changes application behavior for some period of time.
 *   Example: configuration file not found, auth error.
 * - ZF_LOG_INFO - happened significant life cycle event or major state
 *   transition.
 *   Example: app started, user logged in.
 * - ZF_LOG_DEBUG - minimal set of events that could help to reconstruct the
 *   execution path. Usually disabled in release builds.
 * - ZF_LOG_VERBOSE - all other events. Usually disabled in release builds.
 *
 * *Ideally*, log file of debugged, well tested, production ready application
 * should be empty or very small. Choosing a right log level is as important as
 * providing short and self descriptive log message.
 */
#define ZF_LOG_VERBOSE 1
#define ZF_LOG_DEBUG   2
#define ZF_LOG_INFO    3
#define ZF_LOG_WARN    4
#define ZF_LOG_ERROR   5
#define ZF_LOG_FATAL   6
#define ZF_LOG_NONE    0xFF

/* "Current" log level is a compile time check and has no runtime overhead. Log
 * level that is below current log level it said to be "disabled". Otherwise,
 * it's "enabled". Log messages that are disabled has no runtime overhead - they
 * are converted to no-op by preprocessor and then eliminated by compiler.
 * Current log level is configured per compilation module (.c/.cpp/.m file) by
 * defining ZF_LOG_DEF_LEVEL or ZF_LOG_LEVEL. ZF_LOG_LEVEL has higer priority
 * and when defined overrides value provided by ZF_LOG_DEF_LEVEL.
 *
 * Common practice is to define default current log level with ZF_LOG_DEF_LEVEL
 * in build script (e.g. Makefile, CMakeLists.txt, gyp, etc.) for the entire
 * project or target:
 *
 *   CC_ARGS := -DZF_LOG_DEF_LEVEL=ZF_LOG_INFO
 *
 * And when necessary to override it with ZF_LOG_LEVEL in .c/.cpp/.m files
 * before including zf_log.h:
 *
 *   #define ZF_LOG_LEVEL ZF_LOG_VERBOSE
 *   #include <zf_log.h>
 *
 * If both ZF_LOG_DEF_LEVEL and ZF_LOG_LEVEL are undefined, then ZF_LOG_INFO
 * will be used for release builds (NDEBUG is defined) and ZF_LOG_DEBUG
 * otherwise (NDEBUG is not defined).
 */
#if defined(ZF_LOG_LEVEL)
	#define _ZF_LOG_LEVEL ZF_LOG_LEVEL
#elif defined(ZF_LOG_DEF_LEVEL)
	#define _ZF_LOG_LEVEL ZF_LOG_DEF_LEVEL
#else
	#ifdef NDEBUG
		#define _ZF_LOG_LEVEL ZF_LOG_INFO
	#else
		#define _ZF_LOG_LEVEL ZF_LOG_DEBUG
	#endif
#endif

/* "Output" log level is a runtime check. When log level is below output log
 * level it said to be "turned off" (or just "off" for short). Otherwise it's
 * "turned on" (or just "on"). Log levels that were "disabled" (see
 * ZF_LOG_LEVEL and ZF_LOG_DEF_LEVEL) can't be "turned on", but "enabled" log
 * levels could be "turned off". Only messages with log level which is
 * "turned on" will reach output facility. All other messages will be ignored
 * (and their arguments will not be evaluated). Output log level is a global
 * property and configured per process using zf_log_set_output_level() function
 * which can be called at any time.
 *
 * Though in some cases it could be useful to configure output log level per
 * compilation module or per library. There are two ways to achieve that:
 * - Define ZF_LOG_OUTPUT_LEVEL to expresion that evaluates to desired output
 *   log level.
 * - Copy zf_log.h and zf_log.c files into your library and build it with
 *   ZF_LOG_LIBRARY_PREFIX defined to library specific prefix. See
 *   ZF_LOG_LIBRARY_PREFIX for more details.
 *
 * When defined, ZF_LOG_OUTPUT_LEVEL must evaluate to integral value that
 * corresponds to desired output log level. Use it only when compilation module
 * is required to have output log level which is different from global output
 * log level set by zf_log_set_output_level() function. For other cases,
 * consider defining ZF_LOG_LEVEL or using zf_log_set_output_level() function.
 *
 * Example:
 *
 *   #define ZF_LOG_OUTPUT_LEVEL g_module_log_level
 *   #include <zf_log.h>
 *   static int g_module_log_level = ZF_LOG_INFO;
 *   static void foo() {
 *       ZF_LOGI("Will check g_module_log_level for output log level");
 *   }
 *   void debug_log(bool on) {
 *       g_module_log_level = on? ZF_LOG_DEBUG: ZF_LOG_INFO;
 *   }
 *
 * Note on performance. This expression will be evaluated each time message is
 * logged (except when message log level is "disabled" - see ZF_LOG_LEVEL for
 * details). Keep this expression as simple as possible, otherwise it will not
 * only add runtime overhead, but also will increase size of call site (which
 * will result in larger executable). The prefered way is to use integer
 * variable (as in example above). If structure must be used, log_level field
 * must be the first field in this structure:
 *
 *   #define ZF_LOG_OUTPUT_LEVEL (g_config.log_level)
 *   #include <zf_log.h>
 *   struct config {
 *       int log_level;
 *       unsigned other_field;
 *       [...]
 *   };
 *   static config g_config = {ZF_LOG_INFO, 0, ...};
 *
 * This allows compiler to generate more compact load instruction (no need to
 * specify offset since it's zero). Calling a function to get output log level
 * is generaly a bad idea, since it will increase call site size and runtime
 * overhead even further.
 */
#if defined(ZF_LOG_OUTPUT_LEVEL)
	#define _ZF_LOG_OUTPUT_LEVEL ZF_LOG_OUTPUT_LEVEL
#else
	#define _ZF_LOG_OUTPUT_LEVEL _zf_log_global_output_lvl
#endif

/* "Tag" is a compound string that could be associated with a log message. It
 * consists of tag prefix and tag (both are optional).
 *
 * Tag prefix is a global property and configured per process using
 * zf_log_set_tag_prefix() function. Tag prefix identifies context in which
 * component or module is running (e.g. process name). For example, the same
 * library could be used in both client and server processes that work on the
 * same machine. Tag prefix could be used to easily distinguish between them.
 * For more details about tag prefix see zf_log_set_tag_prefix() function. Tag
 * prefix
 *
 * Tag identifies component or module. It is configured per compilation module
 * (.c/.cpp/.m file) by defining ZF_LOG_TAG or ZF_LOG_DEF_TAG. ZF_LOG_TAG has
 * higer priority and when defined overrides value provided by ZF_LOG_DEF_TAG.
 * When defined, value must evaluate to (const char *), so for strings double
 * quotes must be used.
 *
 * Default tag could be defined with ZF_LOG_DEF_TAG in build script (e.g.
 * Makefile, CMakeLists.txt, gyp, etc.) for the entire project or target:
 *
 *   CC_ARGS := -DZF_LOG_DEF_TAG=\"MISC\"
 *
 * And when necessary could be overriden with ZF_LOG_TAG in .c/.cpp/.m files
 * before including zf_log.h:
 *
 *   #define ZF_LOG_TAG "MAIN"
 *   #include <zf_log.h>
 *
 * If both ZF_LOG_DEF_TAG and ZF_LOG_TAG are undefined no tag will be added to
 * the log message (tag prefix still could be added though).
 *
 * Output example:
 *
 *   04-29 22:43:20.244 40059  1299 I hello.MAIN Number of arguments: 1
 *                                    |     |
 *                                    |     +- tag (e.g. module)
 *                                    +- tag prefix (e.g. process name)
 */
#if defined(ZF_LOG_TAG)
	#define _ZF_LOG_TAG ZF_LOG_TAG
#elif defined(ZF_LOG_DEF_TAG)
	#define _ZF_LOG_TAG ZF_LOG_DEF_TAG
#else
	#define _ZF_LOG_TAG 0
#endif

/* Source location is part of a log line that describes location (function or
 * method name, file name and line number, e.g. "runloop@main.cpp:68") of a
 * log statement that produced it.
 * Source location formats are:
 * - ZF_LOG_SRCLOC_NONE - don't add source location to log line.
 * - ZF_LOG_SRCLOC_SHORT - add source location in short form (file and line
 *   number, e.g. "@main.cpp:68").
 * - ZF_LOG_SRCLOC_LONG - add source location in long form (function or method
 *   name, file and line number, e.g. "runloop@main.cpp:68").
 */
#define ZF_LOG_SRCLOC_NONE  0
#define ZF_LOG_SRCLOC_SHORT 1
#define ZF_LOG_SRCLOC_LONG  2

/* Source location format is configured per compilation module (.c/.cpp/.m
 * file) by defining ZF_LOG_DEF_SRCLOC or ZF_LOG_SRCLOC. ZF_LOG_SRCLOC has
 * higer priority and when defined overrides value provided by
 * ZF_LOG_DEF_SRCLOC.
 *
 * Common practice is to define default format with ZF_LOG_DEF_SRCLOC in
 * build script (e.g. Makefile, CMakeLists.txt, gyp, etc.) for the entire
 * project or target:
 *
 *   CC_ARGS := -DZF_LOG_DEF_SRCLOC=ZF_LOG_SRCLOC_LONG
 *
 * And when necessary to override it with ZF_LOG_SRCLOC in .c/.cpp/.m files
 * before including zf_log.h:
 *
 *   #define ZF_LOG_SRCLOC ZF_LOG_SRCLOC_NONE
 *   #include <zf_log.h>
 *
 * If both ZF_LOG_DEF_SRCLOC and ZF_LOG_SRCLOC are undefined, then
 * ZF_LOG_SRCLOC_NONE will be used for release builds (NDEBUG is defined) and
 * ZF_LOG_SRCLOC_LONG otherwise (NDEBUG is not defined).
 */
#if defined(ZF_LOG_SRCLOC)
	#define _ZF_LOG_SRCLOC ZF_LOG_SRCLOC
#elif defined(ZF_LOG_DEF_SRCLOC)
	#define _ZF_LOG_SRCLOC ZF_LOG_DEF_SRCLOC
#else
	#ifdef NDEBUG
		#define _ZF_LOG_SRCLOC ZF_LOG_SRCLOC_NONE
	#else
		#define _ZF_LOG_SRCLOC ZF_LOG_SRCLOC_LONG
	#endif
#endif
#if ZF_LOG_SRCLOC_LONG == _ZF_LOG_SRCLOC
	#define _ZF_LOG_SRCLOC_FUNCTION _ZF_LOG_FUNCTION
#else
	#define _ZF_LOG_SRCLOC_FUNCTION 0
#endif

/* Censoring provides conditional logging of secret information, also known as
 * Personally Identifiable Information (PII) or Sensitive Personal Information
 * (SPI). Censoring can be either enabled (ZF_LOG_CENSORED) or disabled
 * (ZF_LOG_UNCENSORED). When censoring is enabled, log statements marked as
 * "secrets" will be ignored and will have zero overhead (arguments also will
 * not be evaluated).
 */
#define ZF_LOG_CENSORED   1
#define ZF_LOG_UNCENSORED 0

/* Censoring is configured per compilation module (.c/.cpp/.m file) by defining
 * ZF_LOG_DEF_CENSORING or ZF_LOG_CENSORING. ZF_LOG_CENSORING has higer priority
 * and when defined overrides value provided by ZF_LOG_DEF_CENSORING.
 *
 * Common practice is to define default censoring with ZF_LOG_DEF_CENSORING in
 * build script (e.g. Makefile, CMakeLists.txt, gyp, etc.) for the entire
 * project or target:
 *
 *   CC_ARGS := -DZF_LOG_DEF_CENSORING=ZF_LOG_CENSORED
 *
 * And when necessary to override it with ZF_LOG_CENSORING in .c/.cpp/.m files
 * before including zf_log.h (consider doing it only for debug purposes and be
 * very careful not to push such temporary changes to source control):
 *
 *   #define ZF_LOG_CENSORING ZF_LOG_UNCENSORED
 *   #include <zf_log.h>
 *
 * If both ZF_LOG_DEF_CENSORING and ZF_LOG_CENSORING are undefined, then
 * ZF_LOG_CENSORED will be used for release builds (NDEBUG is defined) and
 * ZF_LOG_UNCENSORED otherwise (NDEBUG is not defined).
 */
#if defined(ZF_LOG_CENSORING)
	#define _ZF_LOG_CENSORING ZF_LOG_CENSORING
#elif defined(ZF_LOG_DEF_CENSORING)
	#define _ZF_LOG_CENSORING ZF_LOG_DEF_CENSORING
#else
	#ifdef NDEBUG
		#define _ZF_LOG_CENSORING ZF_LOG_CENSORED
	#else
		#define _ZF_LOG_CENSORING ZF_LOG_UNCENSORED
	#endif
#endif

/* Check censoring at compile time. Evaluates to true when censoring is disabled
 * (i.e. when secrets will be logged). For example:
 *
 *   #if ZF_LOG_SECRETS
 *       char ssn[16];
 *       getSocialSecurityNumber(ssn);
 *       ZF_LOGI("Customer ssn: %s", ssn);
 *   #endif
 *
 * See ZF_LOG_SECRET() macro for a more convenient way of guarding single log
 * statement.
 */
#define ZF_LOG_SECRETS (ZF_LOG_UNCENSORED == _ZF_LOG_CENSORING)

/* Static (compile-time) initialization support allows to configure logging
 * before entering main() function. This mostly useful in C++ where functions
 * and methods could be called during initialization of global objects. Those
 * functions and methods could record log messages too and for that reason
 * static initialization of logging configuration is customizable.
 *
 * Macros below allow to specify values to use for initial configuration:
 * - ZF_LOG_EXTERN_TAG_PREFIX - tag prefix (default: none)
 * - ZF_LOG_EXTERN_GLOBAL_FORMAT - global format options (default: see
 *   ZF_LOG_MEM_WIDTH in zf_log.c)
 * - ZF_LOG_EXTERN_GLOBAL_OUTPUT - global output facility (default: stderr or
 *   platform specific, see ZF_LOG_USE_XXX macros in zf_log.c)
 * - ZF_LOG_EXTERN_GLOBAL_OUTPUT_LEVEL - global output log level (default: 0 -
 *   all levals are "turned on")
 *
 * For example, in log_config.c:
 *
 *   #include <zf_log.h>
 *   ZF_LOG_DEFINE_TAG_PREFIX = "MyApp";
 *   ZF_LOG_DEFINE_GLOBAL_FORMAT = {CUSTOM_MEM_WIDTH};
 *   ZF_LOG_DEFINE_GLOBAL_OUTPUT = {ZF_LOG_PUT_STD, custom_output_callback, 0};
 *   ZF_LOG_DEFINE_GLOBAL_OUTPUT_LEVEL = ZF_LOG_INFO;
 *
 * However, to use any of those macros zf_log library must be compiled with
 * following macros defined:
 * - to use ZF_LOG_DEFINE_TAG_PREFIX define ZF_LOG_EXTERN_TAG_PREFIX
 * - to use ZF_LOG_DEFINE_GLOBAL_FORMAT define ZF_LOG_EXTERN_GLOBAL_FORMAT
 * - to use ZF_LOG_DEFINE_GLOBAL_OUTPUT define ZF_LOG_EXTERN_GLOBAL_OUTPUT
 * - to use ZF_LOG_DEFINE_GLOBAL_OUTPUT_LEVEL define
 *   ZF_LOG_EXTERN_GLOBAL_OUTPUT_LEVEL
 *
 * When zf_log library compiled with one of ZF_LOG_EXTERN_XXX macros defined,
 * corresponding ZF_LOG_DEFINE_XXX macro MUST be used exactly once somewhere.
 * Otherwise build will fail with link error (undefined symbol).
 */
#define ZF_LOG_DEFINE_TAG_PREFIX const char *_zf_log_tag_prefix
#define ZF_LOG_DEFINE_GLOBAL_FORMAT zf_log_format _zf_log_global_format
#define ZF_LOG_DEFINE_GLOBAL_OUTPUT zf_log_output _zf_log_global_output
#define ZF_LOG_DEFINE_GLOBAL_OUTPUT_LEVEL int _zf_log_global_output_lvl

/* Pointer to global format options. Direct modification is not allowed. Use
 * zf_log_set_mem_width() instead. Could be used to initialize zf_log_spec
 * structure:
 *
 *   const zf_log_output g_output = {ZF_LOG_PUT_STD, output_callback, 0};
 *   const zf_log_spec g_spec = {ZF_LOG_GLOBAL_FORMAT, &g_output};
 *   ZF_LOGI_AUX(&g_spec, "Hello");
 */
#define ZF_LOG_GLOBAL_FORMAT ((const zf_log_format *)&_zf_log_global_format)

/* Pointer to global output variable. Direct modification is not allowed. Use
 * zf_log_set_output_v() or zf_log_set_output_p() instead. Could be used to
 * initialize zf_log_spec structure:
 *
 *   const zf_log_format g_format = {40};
 *   const zf_log_spec g_spec = {g_format, ZF_LOG_GLOBAL_OUTPUT};
 *   ZF_LOGI_AUX(&g_spec, "Hello");
 */
#define ZF_LOG_GLOBAL_OUTPUT ((const zf_log_output *)&_zf_log_global_output)

/* When defined, all library symbols produced by linker will be prefixed with
 * provided value. That allows to use zf_log library privately in another
 * libraries without exposing zf_log symbols in their original form (to avoid
 * possible conflicts with other libraries / components that also could use
 * zf_log for logging). Value must be without quotes, for example:
 *
 *   CC_ARGS := -DZF_LOG_LIBRARY_PREFIX=my_lib_
 *
 * Note, that in this mode ZF_LOG_LIBRARY_PREFIX must be defined when building
 * zf_log library AND it also must be defined to the same value when building
 * a library that uses it. For example, consider fictional KittyHttp library
 * that wants to use zf_log for logging. First approach that could be taken is
 * to add zf_log.h and zf_log.c to the KittyHttp's source code tree directly.
 * In that case it will be enough just to define ZF_LOG_LIBRARY_PREFIX in
 * KittyHttp's build script:
 *
 *   // KittyHttp/CMakeLists.txt
 *   target_compile_definitions(KittyHttp PRIVATE
 *                              "ZF_LOG_LIBRARY_PREFIX=KittyHttp_")
 *
 * If KittyHttp doesn't want to include zf_log source code in its source tree
 * and wants to build zf_log as a separate library than zf_log library must be
 * built with ZF_LOG_LIBRARY_PREFIX defined to KittyHttp_ AND KittyHttp library
 * itself also needs to define ZF_LOG_LIBRARY_PREFIX to KittyHttp_. It can do
 * so either in its build script, as in example above, or by providing a
 * wrapper header that KittyHttp library will need to use instead of zf_log.h:
 *
 *   // KittyHttpLogging.h
 *   #define ZF_LOG_LIBRARY_PREFIX KittyHttp_
 *   #include <zf_log.h>
 *
 * Regardless of the method chosen, the end result is that zf_log symbols will
 * be prefixed with "KittyHttp_", so if a user of KittyHttp (say DogeBrowser)
 * also uses zf_log for logging, they will not interferer with each other. Both
 * will have their own log level, output facility, format options etc.
 */
#ifdef ZF_LOG_LIBRARY_PREFIX
	#define _ZF_LOG_DECOR__(prefix, name) prefix ## name
	#define _ZF_LOG_DECOR_(prefix, name) _ZF_LOG_DECOR__(prefix, name)
	#define _ZF_LOG_DECOR(name) _ZF_LOG_DECOR_(ZF_LOG_LIBRARY_PREFIX, name)

	#define zf_log_set_tag_prefix _ZF_LOG_DECOR(zf_log_set_tag_prefix)
	#define zf_log_set_mem_width _ZF_LOG_DECOR(zf_log_set_mem_width)
	#define zf_log_set_output_level _ZF_LOG_DECOR(zf_log_set_output_level)
	#define zf_log_set_output_v _ZF_LOG_DECOR(zf_log_set_output_v)
	#define zf_log_set_output_p _ZF_LOG_DECOR(zf_log_set_output_p)
	#define zf_log_out_stderr_callback _ZF_LOG_DECOR(zf_log_out_stderr_callback)
	#define _zf_log_tag_prefix _ZF_LOG_DECOR(_zf_log_tag_prefix)
	#define _zf_log_global_format _ZF_LOG_DECOR(_zf_log_global_format)
	#define _zf_log_global_output _ZF_LOG_DECOR(_zf_log_global_output)
	#define _zf_log_global_output_lvl _ZF_LOG_DECOR(_zf_log_global_output_lvl)
	#define _zf_log_write_d _ZF_LOG_DECOR(_zf_log_write_d)
	#define _zf_log_write_aux_d _ZF_LOG_DECOR(_zf_log_write_aux_d)
	#define _zf_log_write _ZF_LOG_DECOR(_zf_log_write)
	#define _zf_log_write_aux _ZF_LOG_DECOR(_zf_log_write_aux)
	#define _zf_log_write_mem_d _ZF_LOG_DECOR(_zf_log_write_mem_d)
	#define _zf_log_write_mem_aux_d _ZF_LOG_DECOR(_zf_log_write_mem_aux_d)
	#define _zf_log_write_mem _ZF_LOG_DECOR(_zf_log_write_mem)
	#define _zf_log_write_mem_aux _ZF_LOG_DECOR(_zf_log_write_mem_aux)
	#define _zf_log_stderr_spec _ZF_LOG_DECOR(_zf_log_stderr_spec)
#endif

#if defined(__printflike)
	#define _ZF_LOG_PRINTFLIKE(str_index, first_to_check) \
		__printflike(str_index, first_to_check)
#elif defined(__GNUC__)
	#define _ZF_LOG_PRINTFLIKE(str_index, first_to_check) \
		__attribute__((format(__printf__, str_index, first_to_check)))
#else
	#define _ZF_LOG_PRINTFLIKE(str_index, first_to_check)
#endif

#if (defined(_WIN32) || defined(_WIN64)) && !defined(__GNUC__)
	#define _ZF_LOG_FUNCTION __FUNCTION__
#else
	#define _ZF_LOG_FUNCTION __func__
#endif

#if defined(_MSC_VER) && !defined(__INTEL_COMPILER)
	#define _ZF_LOG_INLINE __inline
	#define _ZF_LOG_IF(cond) \
		__pragma(warning(push)) \
		__pragma(warning(disable:4127)) \
		if(cond) \
		__pragma(warning(pop))
	#define _ZF_LOG_WHILE(cond) \
		__pragma(warning(push)) \
		__pragma(warning(disable:4127)) \
		while(cond) \
		__pragma(warning(pop))
#else
	#define _ZF_LOG_INLINE inline
	#define _ZF_LOG_IF(cond) if(cond)
	#define _ZF_LOG_WHILE(cond) while(cond)
#endif
#define _ZF_LOG_NEVER _ZF_LOG_IF(0)
#define _ZF_LOG_ONCE _ZF_LOG_WHILE(0)

#ifdef __cplusplus
extern "C" {
#endif

/* Set tag prefix. Prefix will be separated from the tag with dot ('.').
 * Use 0 or empty string to disable (default). Common use is to set it to
 * the process (or build target) name (e.g. to separate client and server
 * processes). Function will NOT copy provided prefix string, but will store the
 * pointer. Hence specified prefix string must remain valid. See
 * ZF_LOG_DEFINE_TAG_PREFIX for a way to set it before entering main() function.
 * See ZF_LOG_TAG for more information about tag and tag prefix.
 */
void zf_log_set_tag_prefix(const char *const prefix);

/* Set number of bytes per log line in memory (ASCII-HEX) output. Example:
 *
 *   I hello.MAIN 4c6f72656d20697073756d20646f6c6f  Lorem ipsum dolo
 *                |<-          w bytes         ->|  |<-  w chars ->|
 *
 * See ZF_LOGF_MEM and ZF_LOGF_MEM_AUX for more details.
 */
void zf_log_set_mem_width(const unsigned w);

/* Set "output" log level. See ZF_LOG_LEVEL and ZF_LOG_OUTPUT_LEVEL for more
 * info about log levels.
 */
void zf_log_set_output_level(const int lvl);

/* Put mask is a set of flags that define what fields will be added to each
 * log message. Default value is ZF_LOG_PUT_STD and other flags could be used to
 * alter its behavior. See zf_log_set_output_v() for more details.
 *
 * Note about ZF_LOG_PUT_SRC: it will be added only in debug builds (NDEBUG is
 * not defined).
 */
enum
{
	ZF_LOG_PUT_CTX = 1 << 0, /* context (time, pid, tid, log level) */
	ZF_LOG_PUT_TAG = 1 << 1, /* tag (including tag prefix) */
	ZF_LOG_PUT_SRC = 1 << 2, /* source location (file, line, function) */
	ZF_LOG_PUT_MSG = 1 << 3, /* message text (formatted string) */
	ZF_LOG_PUT_STD = 0xffff, /* everything (default) */
};

typedef struct zf_log_message
{
	int lvl; /* Log level of the message */
	const char *tag; /* Associated tag (without tag prefix) */
	char *buf; /* Buffer start */
	char *e; /* Buffer end (last position where EOL with 0 could be written) */
	char *p; /* Buffer content end (append position) */
	char *tag_b; /* Prefixed tag start */
	char *tag_e; /* Prefixed tag end (if != tag_b, points to msg separator) */
	char *msg_b; /* Message start (expanded format string) */
}
zf_log_message;

/* Type of output callback function. It will be called for each log line allowed
 * by both "current" and "output" log levels ("enabled" and "turned on").
 * Callback function is allowed to modify content of the buffers pointed by the
 * msg, but it's not allowed to modify any of msg fields. Buffer pointed by msg
 * is UTF-8 encoded (no BOM mark).
 */
typedef void (*zf_log_output_cb)(const zf_log_message *msg, void *arg);

/* Format options. For more details see zf_log_set_mem_width().
 */
typedef struct zf_log_format
{
	unsigned mem_width; /* Bytes per line in memory (ASCII-HEX) dump */
}
zf_log_format;

/* Output facility.
 */
typedef struct zf_log_output
{
	unsigned mask; /* What to put into log line buffer (see ZF_LOG_PUT_XXX) */
	void *arg; /* User provided output callback argument */
	zf_log_output_cb callback; /* Output callback function */
}
zf_log_output;

/* Set output callback function.
 *
 * Mask allows to control what information will be added to the log line buffer
 * before callback function is invoked. Default mask value is ZF_LOG_PUT_STD.
 */
void zf_log_set_output_v(const unsigned mask, void *const arg,
						 const zf_log_output_cb callback);
static _ZF_LOG_INLINE void zf_log_set_output_p(const zf_log_output *const output)
{
	zf_log_set_output_v(output->mask, output->arg, output->callback);
}

/* Used with _AUX macros and allows to override global format and output
 * facility. Use ZF_LOG_GLOBAL_FORMAT and ZF_LOG_GLOBAL_OUTPUT for values from
 * global configuration. Example:
 *
 *   static const zf_log_output module_output = {
 *       ZF_LOG_PUT_STD, 0, custom_output_callback
 *   };
 *   static const zf_log_spec module_spec = {
 *       ZF_LOG_GLOBAL_FORMAT, &module_output
 *   };
 *   ZF_LOGI_AUX(&module_spec, "Position: %ix%i", x, y);
 *
 * See ZF_LOGF_AUX and ZF_LOGF_MEM_AUX for details.
 */
typedef struct zf_log_spec
{
	const zf_log_format *format;
	const zf_log_output *output;
}
zf_log_spec;

#ifdef __cplusplus
}
#endif

/* Execute log statement if condition is true. Example:
 *
 *   ZF_LOG_IF(1 < 2, ZF_LOGI("Log this"));
 *   ZF_LOG_IF(1 > 2, ZF_LOGI("Don't log this"));
 *
 * Keep in mind though, that if condition can't be evaluated at compile time,
 * then it will be evaluated at run time. This will increase exectuable size
 * and can have noticeable performance overhead. Try to limit conditions to
 * expressions that can be evaluated at compile time.
 */
#define ZF_LOG_IF(cond, f) do { _ZF_LOG_IF((cond)) { f; } } _ZF_LOG_ONCE

/* Mark log statement as "secret". Log statements that are marked as secrets
 * will NOT be executed when censoring is enabled (see ZF_LOG_CENSORED).
 * Example:
 *
 *   ZF_LOG_SECRET(ZF_LOGI("Credit card: %s", credit_card));
 *   ZF_LOG_SECRET(ZF_LOGD_MEM(cipher, cipher_sz, "Cipher bytes:"));
 */
#define ZF_LOG_SECRET(f) ZF_LOG_IF(ZF_LOG_SECRETS, f)

/* Check "current" log level at compile time (ignoring "output" log level).
 * Evaluates to true when specified log level is enabled. For example:
 *
 *   #if ZF_LOG_ENABLED_DEBUG
 *       const char *const g_enum_strings[] = {
 *           "enum_value_0", "enum_value_1", "enum_value_2"
 *       };
 *   #endif
 *   // ...
 *   #if ZF_LOG_ENABLED_DEBUG
 *       ZF_LOGD("enum value: %s", g_enum_strings[v]);
 *   #endif
 *
 * See ZF_LOG_LEVEL for details.
 */
#define ZF_LOG_ENABLED(lvl)     ((lvl) >= _ZF_LOG_LEVEL)
#define ZF_LOG_ENABLED_VERBOSE  ZF_LOG_ENABLED(ZF_LOG_VERBOSE)
#define ZF_LOG_ENABLED_DEBUG    ZF_LOG_ENABLED(ZF_LOG_DEBUG)
#define ZF_LOG_ENABLED_INFO     ZF_LOG_ENABLED(ZF_LOG_INFO)
#define ZF_LOG_ENABLED_WARN     ZF_LOG_ENABLED(ZF_LOG_WARN)
#define ZF_LOG_ENABLED_ERROR    ZF_LOG_ENABLED(ZF_LOG_ERROR)
#define ZF_LOG_ENABLED_FATAL    ZF_LOG_ENABLED(ZF_LOG_FATAL)

/* Check "output" log level at run time (taking into account "current" log
 * level as well). Evaluates to true when specified log level is turned on AND
 * enabled. For example:
 *
 *   if (ZF_LOG_ON_DEBUG)
 *   {
 *       char hash[65];
 *       sha256(data_ptr, data_sz, hash);
 *       ZF_LOGD("data: len=%u, sha256=%s", data_sz, hash);
 *   }
 *
 * See ZF_LOG_OUTPUT_LEVEL for details.
 */
#define ZF_LOG_ON(lvl) \
		(ZF_LOG_ENABLED((lvl)) && (lvl) >= _ZF_LOG_OUTPUT_LEVEL)
#define ZF_LOG_ON_VERBOSE   ZF_LOG_ON(ZF_LOG_VERBOSE)
#define ZF_LOG_ON_DEBUG     ZF_LOG_ON(ZF_LOG_DEBUG)
#define ZF_LOG_ON_INFO      ZF_LOG_ON(ZF_LOG_INFO)
#define ZF_LOG_ON_WARN      ZF_LOG_ON(ZF_LOG_WARN)
#define ZF_LOG_ON_ERROR     ZF_LOG_ON(ZF_LOG_ERROR)
#define ZF_LOG_ON_FATAL     ZF_LOG_ON(ZF_LOG_FATAL)

#ifdef __cplusplus
extern "C" {
#endif

extern const char *_zf_log_tag_prefix;
extern zf_log_format _zf_log_global_format;
extern zf_log_output _zf_log_global_output;
extern int _zf_log_global_output_lvl;
extern const zf_log_spec _zf_log_stderr_spec;

void _zf_log_write_d(
		const char *const func, const char *const file, const unsigned line,
		const int lvl, const char *const tag,
		const char *const fmt, ...) _ZF_LOG_PRINTFLIKE(6, 7);
void _zf_log_write_aux_d(
		const char *const func, const char *const file, const unsigned line,
		const zf_log_spec *const log, const int lvl, const char *const tag,
		const char *const fmt, ...) _ZF_LOG_PRINTFLIKE(7, 8);
void _zf_log_write(
		const int lvl, const char *const tag,
		const char *const fmt, ...) _ZF_LOG_PRINTFLIKE(3, 4);
void _zf_log_write_aux(
		const zf_log_spec *const log, const int lvl, const char *const tag,
		const char *const fmt, ...) _ZF_LOG_PRINTFLIKE(4, 5);
void _zf_log_write_mem_d(
		const char *const func, const char *const file, const unsigned line,
		const int lvl, const char *const tag,
		const void *const d, const unsigned d_sz,
		const char *const fmt, ...) _ZF_LOG_PRINTFLIKE(8, 9);
void _zf_log_write_mem_aux_d(
		const char *const func, const char *const file, const unsigned line,
		const zf_log_spec *const log, const int lvl, const char *const tag,
		const void *const d, const unsigned d_sz,
		const char *const fmt, ...) _ZF_LOG_PRINTFLIKE(9, 10);
void _zf_log_write_mem(
		const int lvl, const char *const tag,
		const void *const d, const unsigned d_sz,
		const char *const fmt, ...) _ZF_LOG_PRINTFLIKE(5, 6);
void _zf_log_write_mem_aux(
		const zf_log_spec *const log, const int lvl, const char *const tag,
		const void *const d, const unsigned d_sz,
		const char *const fmt, ...) _ZF_LOG_PRINTFLIKE(6, 7);

#ifdef __cplusplus
}
#endif

/* Message logging macros:
 * - ZF_LOGV("format string", args, ...)
 * - ZF_LOGD("format string", args, ...)
 * - ZF_LOGI("format string", args, ...)
 * - ZF_LOGW("format string", args, ...)
 * - ZF_LOGE("format string", args, ...)
 * - ZF_LOGF("format string", args, ...)
 *
 * Memory logging macros:
 * - ZF_LOGV_MEM(data_ptr, data_sz, "format string", args, ...)
 * - ZF_LOGD_MEM(data_ptr, data_sz, "format string", args, ...)
 * - ZF_LOGI_MEM(data_ptr, data_sz, "format string", args, ...)
 * - ZF_LOGW_MEM(data_ptr, data_sz, "format string", args, ...)
 * - ZF_LOGE_MEM(data_ptr, data_sz, "format string", args, ...)
 * - ZF_LOGF_MEM(data_ptr, data_sz, "format string", args, ...)
 *
 * Auxiliary logging macros:
 * - ZF_LOGV_AUX(&log_instance, "format string", args, ...)
 * - ZF_LOGD_AUX(&log_instance, "format string", args, ...)
 * - ZF_LOGI_AUX(&log_instance, "format string", args, ...)
 * - ZF_LOGW_AUX(&log_instance, "format string", args, ...)
 * - ZF_LOGE_AUX(&log_instance, "format string", args, ...)
 * - ZF_LOGF_AUX(&log_instance, "format string", args, ...)
 *
 * Auxiliary memory logging macros:
 * - ZF_LOGV_MEM_AUX(&log_instance, data_ptr, data_sz, "format string", args, ...)
 * - ZF_LOGD_MEM_AUX(&log_instance, data_ptr, data_sz, "format string", args, ...)
 * - ZF_LOGI_MEM_AUX(&log_instance, data_ptr, data_sz, "format string", args, ...)
 * - ZF_LOGW_MEM_AUX(&log_instance, data_ptr, data_sz, "format string", args, ...)
 * - ZF_LOGE_MEM_AUX(&log_instance, data_ptr, data_sz, "format string", args, ...)
 * - ZF_LOGF_MEM_AUX(&log_instance, data_ptr, data_sz, "format string", args, ...)
 *
 * Preformatted string logging macros:
 * - ZF_LOGV_STR("preformatted string");
 * - ZF_LOGD_STR("preformatted string");
 * - ZF_LOGI_STR("preformatted string");
 * - ZF_LOGW_STR("preformatted string");
 * - ZF_LOGE_STR("preformatted string");
 * - ZF_LOGF_STR("preformatted string");
 *
 * Explicit log level and tag macros:
 * - ZF_LOG_WRITE(level, tag, "format string", args, ...)
 * - ZF_LOG_WRITE_MEM(level, tag, data_ptr, data_sz, "format string", args, ...)
 * - ZF_LOG_WRITE_AUX(&log_instance, level, tag, "format string", args, ...)
 * - ZF_LOG_WRITE_MEM_AUX(&log_instance, level, tag, data_ptr, data_sz,
 *                        "format string", args, ...)
 *
 * Format string follows printf() conventions. Both data_ptr and data_sz could
 * be 0. Tag can be 0 as well. Most compilers will verify that type of arguments
 * match format specifiers in format string.
 *
 * Library assuming UTF-8 encoding for all strings (char *), including format
 * string itself.
 */
#if ZF_LOG_SRCLOC_NONE == _ZF_LOG_SRCLOC
	#define ZF_LOG_WRITE(lvl, tag, ...) \
			do { \
				if (ZF_LOG_ON(lvl)) \
					_zf_log_write(lvl, tag, __VA_ARGS__); \
			} _ZF_LOG_ONCE
	#define ZF_LOG_WRITE_MEM(lvl, tag, d, d_sz, ...) \
			do { \
				if (ZF_LOG_ON(lvl)) \
					_zf_log_write_mem(lvl, tag, d, d_sz, __VA_ARGS__); \
			} _ZF_LOG_ONCE
	#define ZF_LOG_WRITE_AUX(log, lvl, tag, ...) \
			do { \
				if (ZF_LOG_ON(lvl)) \
					_zf_log_write_aux(log, lvl, tag, __VA_ARGS__); \
			} _ZF_LOG_ONCE
	#define ZF_LOG_WRITE_MEM_AUX(log, lvl, tag, d, d_sz, ...) \
			do { \
				if (ZF_LOG_ON(lvl)) \
					_zf_log_write_mem_aux(log, lvl, tag, d, d_sz, __VA_ARGS__); \
			} _ZF_LOG_ONCE
#else
	#define ZF_LOG_WRITE(lvl, tag, ...) \
			do { \
				if (ZF_LOG_ON(lvl)) \
					_zf_log_write_d(_ZF_LOG_SRCLOC_FUNCTION, __FILE__, __LINE__, \
							lvl, tag, __VA_ARGS__); \
			} _ZF_LOG_ONCE
	#define ZF_LOG_WRITE_MEM(lvl, tag, d, d_sz, ...) \
			do { \
				if (ZF_LOG_ON(lvl)) \
					_zf_log_write_mem_d(_ZF_LOG_SRCLOC_FUNCTION, __FILE__, __LINE__, \
							lvl, tag, d, d_sz, __VA_ARGS__); \
			} _ZF_LOG_ONCE
	#define ZF_LOG_WRITE_AUX(log, lvl, tag, ...) \
			do { \
				if (ZF_LOG_ON(lvl)) \
					_zf_log_write_aux_d(_ZF_LOG_SRCLOC_FUNCTION, __FILE__, __LINE__, \
							log, lvl, tag, __VA_ARGS__); \
			} _ZF_LOG_ONCE
	#define ZF_LOG_WRITE_MEM_AUX(log, lvl, tag, d, d_sz, ...) \
			do { \
				if (ZF_LOG_ON(lvl)) \
					_zf_log_write_mem_aux_d(_ZF_LOG_SRCLOC_FUNCTION, __FILE__, __LINE__, \
							log, lvl, tag, d, d_sz, __VA_ARGS__); \
			} _ZF_LOG_ONCE
#endif

static _ZF_LOG_INLINE void _zf_log_unused(const int dummy, ...) {(void)dummy;}

#define _ZF_LOG_UNUSED(...) \
		do { _ZF_LOG_NEVER _zf_log_unused(0, __VA_ARGS__); } _ZF_LOG_ONCE

#if ZF_LOG_ENABLED_VERBOSE
	#define ZF_LOGV(...) \
			ZF_LOG_WRITE(ZF_LOG_VERBOSE, _ZF_LOG_TAG, __VA_ARGS__)
	#define ZF_LOGV_AUX(log, ...) \
			ZF_LOG_WRITE_AUX(log, ZF_LOG_VERBOSE, _ZF_LOG_TAG, __VA_ARGS__)
	#define ZF_LOGV_MEM(d, d_sz, ...) \
			ZF_LOG_WRITE_MEM(ZF_LOG_VERBOSE, _ZF_LOG_TAG, d, d_sz, __VA_ARGS__)
	#define ZF_LOGV_MEM_AUX(log, d, d_sz, ...) \
			ZF_LOG_WRITE_MEM(log, ZF_LOG_VERBOSE, _ZF_LOG_TAG, d, d_sz, __VA_ARGS__)
#else
	#define ZF_LOGV(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGV_AUX(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGV_MEM(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGV_MEM_AUX(...) _ZF_LOG_UNUSED(__VA_ARGS__)
#endif

#if ZF_LOG_ENABLED_DEBUG
	#define ZF_LOGD(...) \
			ZF_LOG_WRITE(ZF_LOG_DEBUG, _ZF_LOG_TAG, __VA_ARGS__)
	#define ZF_LOGD_AUX(log, ...) \
			ZF_LOG_WRITE_AUX(log, ZF_LOG_DEBUG, _ZF_LOG_TAG, __VA_ARGS__)
	#define ZF_LOGD_MEM(d, d_sz, ...) \
			ZF_LOG_WRITE_MEM(ZF_LOG_DEBUG, _ZF_LOG_TAG, d, d_sz, __VA_ARGS__)
	#define ZF_LOGD_MEM_AUX(log, d, d_sz, ...) \
			ZF_LOG_WRITE_MEM_AUX(log, ZF_LOG_DEBUG, _ZF_LOG_TAG, d, d_sz, __VA_ARGS__)
#else
	#define ZF_LOGD(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGD_AUX(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGD_MEM(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGD_MEM_AUX(...) _ZF_LOG_UNUSED(__VA_ARGS__)
#endif

#if ZF_LOG_ENABLED_INFO
	#define ZF_LOGI(...) \
			ZF_LOG_WRITE(ZF_LOG_INFO, _ZF_LOG_TAG, __VA_ARGS__)
	#define ZF_LOGI_AUX(log, ...) \
			ZF_LOG_WRITE_AUX(log, ZF_LOG_INFO, _ZF_LOG_TAG, __VA_ARGS__)
	#define ZF_LOGI_MEM(d, d_sz, ...) \
			ZF_LOG_WRITE_MEM(ZF_LOG_INFO, _ZF_LOG_TAG, d, d_sz, __VA_ARGS__)
	#define ZF_LOGI_MEM_AUX(log, d, d_sz, ...) \
			ZF_LOG_WRITE_MEM_AUX(log, ZF_LOG_INFO, _ZF_LOG_TAG, d, d_sz, __VA_ARGS__)
#else
	#define ZF_LOGI(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGI_AUX(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGI_MEM(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGI_MEM_AUX(...) _ZF_LOG_UNUSED(__VA_ARGS__)
#endif

#if ZF_LOG_ENABLED_WARN
	#define ZF_LOGW(...) \
			ZF_LOG_WRITE(ZF_LOG_WARN, _ZF_LOG_TAG, __VA_ARGS__)
	#define ZF_LOGW_AUX(log, ...) \
			ZF_LOG_WRITE_AUX(log, ZF_LOG_WARN, _ZF_LOG_TAG, __VA_ARGS__)
	#define ZF_LOGW_MEM(d, d_sz, ...) \
			ZF_LOG_WRITE_MEM(ZF_LOG_WARN, _ZF_LOG_TAG, d, d_sz, __VA_ARGS__)
	#define ZF_LOGW_MEM_AUX(log, d, d_sz, ...) \
			ZF_LOG_WRITE_MEM_AUX(log, ZF_LOG_WARN, _ZF_LOG_TAG, d, d_sz, __VA_ARGS__)
#else
	#define ZF_LOGW(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGW_AUX(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGW_MEM(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGW_MEM_AUX(...) _ZF_LOG_UNUSED(__VA_ARGS__)
#endif

#if ZF_LOG_ENABLED_ERROR
	#define ZF_LOGE(...) \
			ZF_LOG_WRITE(ZF_LOG_ERROR, _ZF_LOG_TAG, __VA_ARGS__)
	#define ZF_LOGE_AUX(log, ...) \
			ZF_LOG_WRITE_AUX(log, ZF_LOG_ERROR, _ZF_LOG_TAG, __VA_ARGS__)
	#define ZF_LOGE_MEM(d, d_sz, ...) \
			ZF_LOG_WRITE_MEM(ZF_LOG_ERROR, _ZF_LOG_TAG, d, d_sz, __VA_ARGS__)
	#define ZF_LOGE_MEM_AUX(log, d, d_sz, ...) \
			ZF_LOG_WRITE_MEM_AUX(log, ZF_LOG_ERROR, _ZF_LOG_TAG, d, d_sz, __VA_ARGS__)
#else
	#define ZF_LOGE(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGE_AUX(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGE_MEM(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGE_MEM_AUX(...) _ZF_LOG_UNUSED(__VA_ARGS__)
#endif

#if ZF_LOG_ENABLED_FATAL
	#define ZF_LOGF(...) \
			ZF_LOG_WRITE(ZF_LOG_FATAL, _ZF_LOG_TAG, __VA_ARGS__)
	#define ZF_LOGF_AUX(log, ...) \
			ZF_LOG_WRITE_AUX(log, ZF_LOG_FATAL, _ZF_LOG_TAG, __VA_ARGS__)
	#define ZF_LOGF_MEM(d, d_sz, ...) \
			ZF_LOG_WRITE_MEM(ZF_LOG_FATAL, _ZF_LOG_TAG, d, d_sz, __VA_ARGS__)
	#define ZF_LOGF_MEM_AUX(log, d, d_sz, ...) \
			ZF_LOG_WRITE_MEM_AUX(log, ZF_LOG_FATAL, _ZF_LOG_TAG, d, d_sz, __VA_ARGS__)
#else
	#define ZF_LOGF(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGF_AUX(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGF_MEM(...) _ZF_LOG_UNUSED(__VA_ARGS__)
	#define ZF_LOGF_MEM_AUX(...) _ZF_LOG_UNUSED(__VA_ARGS__)
#endif

#define ZF_LOGV_STR(s) ZF_LOGV("%s", (s))
#define ZF_LOGD_STR(s) ZF_LOGD("%s", (s))
#define ZF_LOGI_STR(s) ZF_LOGI("%s", (s))
#define ZF_LOGW_STR(s) ZF_LOGW("%s", (s))
#define ZF_LOGE_STR(s) ZF_LOGE("%s", (s))
#define ZF_LOGF_STR(s) ZF_LOGF("%s", (s))

#ifdef __cplusplus
extern "C" {
#endif

/* Output to standard error stream. Library uses it by default, though in few
 * cases it could be necessary to specify it explicitly. For example, when
 * zf_log library is compiled with ZF_LOG_EXTERN_GLOBAL_OUTPUT, application must
 * define and initialize global output variable:
 *
 *   ZF_LOG_DEFINE_GLOBAL_OUTPUT = {ZF_LOG_OUT_STDERR};
 *
 * Another example is when using custom output, stderr could be used as a
 * fallback when custom output facility failed to initialize:
 *
 *   zf_log_set_output_v(ZF_LOG_OUT_STDERR);
 */
enum { ZF_LOG_OUT_STDERR_MASK = ZF_LOG_PUT_STD };
void zf_log_out_stderr_callback(const zf_log_message *const msg, void *arg);
#define ZF_LOG_OUT_STDERR ZF_LOG_OUT_STDERR_MASK, 0, zf_log_out_stderr_callback

/* Predefined spec for stderr. Uses global format options (ZF_LOG_GLOBAL_FORMAT)
 * and ZF_LOG_OUT_STDERR. Could be used to force output to stderr for a
 * particular message. Example:
 *
 *   f = fopen("foo.log", "w");
 *   if (!f)
 *       ZF_LOGE_AUX(ZF_LOG_STDERR, "Failed to open log file");
 */
#define ZF_LOG_STDERR (&_zf_log_stderr_spec)

#ifdef __cplusplus
}
#endif

#endif
