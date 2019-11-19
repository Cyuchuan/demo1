package com.cyc.demo1.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

/**
 * 配合@CsvBindByName、@CsvBindByPosition等注解使用
 * 
 * @author chenyuchuan
 */
public class CsvToBeanUtil {
    public static final String DEFAULT_SEPARATOR_STR = "\u0001";
    public static final char DEFAULT_SEPARATOR_CHAR = '\u0001';
    private static final char DEFAULT_ESCAPE_CHAR = '\u0000';
    private static final char DEFAULT_NO_QUOTE_CHAR = '\u0000';
    private static final char DEFAULT_QUOTE_CHAR = '"';

    private static final String TEMP_DIR;

    // 开始对临时文件夹的地址的设置，可以在配置文件中进行设置，也可以使用默认
    static {
        TEMP_DIR = System.getProperty("csvToBeanUtil.tempDir", "tempCsvBeanUtilTarget");
    }

    private CsvToBeanUtil() {

    }

    /**
     * @see CsvToBeanUtil#getCsvWriter(BufferedWriter, char, boolean)
     */
    public static StatefulBeanToCsv getCsvWriter(BufferedWriter bufferedWriter, char separator) {
        return getCsvWriter(bufferedWriter, separator, false);
    }

    /**
     * 这个api可以获取输出csv的writer，可以自行进行csv的文件输出，可以一个对象，或者一个小list。{@code bufferedWriter}自己控制流的关闭
     *
     * @param bufferedWriter
     *            输出的文件writer
     * @param separator
     *            csv的分隔符，这里只支持单个字符,如果char已经可以满足你的需求，则不需要后续处理，推荐用
     *            {@link CsvToBeanUtil#DEFAULT_SEPARATOR_CHAR}。如果需要分隔符变为其他字符串，请在文件完全生成后调用{@link CsvToBeanUtil#toCanProcessFile(File, Charset, String, String)}
     * @param needQuote
     *            是否需要双引号括住字段
     * @return 返回csvWriter，自行调用 {@link StatefulBeanToCsv#write(List)} 或者 {@link StatefulBeanToCsv#write(Object)}
     */
    public static StatefulBeanToCsv getCsvWriter(BufferedWriter bufferedWriter, char separator, boolean needQuote) {
        if (bufferedWriter == null) {
            throw new IllegalArgumentException("输入参数不能为空");
        }

        char quoteChar = getQuoteChar(needQuote);

        return new StatefulBeanToCsvBuilder(bufferedWriter).withLineEnd(System.lineSeparator()).withQuotechar(quoteChar)
            .withOrderedResults(true).withEscapechar(DEFAULT_ESCAPE_CHAR).withSeparator(separator).build();
    }

    /**
     * @see CsvToBeanUtil#writeBeansToFile(List, File, Charset, String, boolean)
     *
     */
    public static <T> void writeBeansToFile(List<T> beans, File destFile, Charset encode, String separator) {
        writeBeansToFile(beans, destFile, encode, separator, false);
    }

    /**
     * 一次性输出list中的所有bean到文件，不能append， 根据传入的List定义的csv对象（csv的相关注解），输出对象的内容到指定的destFile文件中。
     * 使用该api进行对象到csv文件的输出时，尽量是用@CsvBindByPosition根据位置进行输出。
     * 因为使用@CsvBindByName，当没有一个对象的时候（即空文件）无法获得对象的字段信息绑定，因此空文件是不带header的（即字段的注解），可能与预期不符。
     * 
     * @param beans
     *            需要输出的csv对象的List
     * @param destFile
     *            输出到的目标文件
     * @param encode
     *            文件的编码格式
     * @param separator
     *            csv中的分隔符，可以是任意string
     * @param needQuote
     *            输出的字段是否需要双引号括住
     */
    @SuppressWarnings("unchecked")
    public static <T> void writeBeansToFile(List<T> beans, File destFile, Charset encode, String separator,
        boolean needQuote) {
        if (beans == null || destFile == null || encode == null || StringUtils.isEmpty(separator)) {
            throw new IllegalArgumentException("输入参数不能为空");
        }

        File tempFile = getTempFile();

        File correctFile = null;
        try (FileOutputStream tempFileOutputStream = FileUtils.openOutputStream(tempFile);
            BufferedWriter tempWriter = new BufferedWriter(new OutputStreamWriter(tempFileOutputStream, encode))) {

            char quoteChar = getQuoteChar(needQuote);

            new StatefulBeanToCsvBuilder(tempWriter).withLineEnd(System.lineSeparator()).withQuotechar(quoteChar)
                .withOrderedResults(true).withEscapechar(DEFAULT_ESCAPE_CHAR).withSeparator(DEFAULT_SEPARATOR_CHAR)
                .build().write(beans);
            tempWriter.flush();

            correctFile = toCanProcessFile(tempFile, encode, DEFAULT_SEPARATOR_STR, separator);
            if (correctFile == null) {
                if (!destFile.exists()) {
                    destFile.createNewFile();
                } else {
                    destFile.delete();
                    destFile.createNewFile();
                }
            } else {
                FileUtils.copyFile(correctFile, destFile);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            FileUtils.deleteQuietly(tempFile);
            FileUtils.deleteQuietly(correctFile);

        }
    }

    private static char getQuoteChar(boolean needQuote) {
        char quoteChar;
        if (needQuote) {
            quoteChar = DEFAULT_QUOTE_CHAR;

        } else {
            quoteChar = DEFAULT_NO_QUOTE_CHAR;

        }
        return quoteChar;
    }

    public static <T> List<T> toBeanList(File srcFile, Charset encode, String separator, Class<T> type) {
        return toBeanList(srcFile, encode, separator, type, false);
    }

    /**
     * 一次性读出文件 使用注解@CsvBindByName（根据第一行的行头名称进行映射）或者@CsvBindByPosition（根据字段的位置进行映射，从0开始）标识class的字段
     *
     * @param srcFile
     *            csv文件
     * @param encode
     *            文件的编码格式
     * @param separator
     *            分隔符
     * @param type
     *            要转换的class
     * 
     * @return 对应文件的list列表
     */
    public static <T> List<T> toBeanList(File srcFile, Charset encode, String separator, Class<T> type,
        boolean needQuote) {
        if (srcFile == null) {
            throw new IllegalArgumentException("输入参数不能为空");
        }

        try (FileInputStream fileInputStream = FileUtils.openInputStream(srcFile)) {
            return toBeanList(fileInputStream, encode, separator, type, needQuote);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see CsvToBeanUtil#toBeanList(InputStream, Charset, String, Class, boolean)
     */
    public static <T> List<T> toBeanList(InputStream inputStream, Charset encode, String separator, Class<T> type) {
        return toBeanList(inputStream, encode, separator, type, false);
    }

    /**
     * 一次性读出文件 使用注解@CsvBindByName（根据第一行的行头名称进行映射）或者@CsvBindByPosition（根据字段的位置进行映射，从0开始）标识class的字段
     * 因为内部会一次性读出流，所以内部也会进行流的关闭，调用方不进行关闭也不会有问题。要和{@link CsvToBeanUtil#toBeanIterator(InputStream, Charset, Class)}区分，这个需要自己用完后关闭流
     * 
     * @param inputStream
     *            csv文件流，或者网络文件流
     * @param encode
     *            文件的编码格式
     * @param separator
     *            分隔符
     * @param type
     *            要转换的class
     * @param needQuote
     *            原csv是否有双引号括住字段
     * @return 对应文件的list列表
     */
    public static <T> List<T> toBeanList(InputStream inputStream, Charset encode, String separator, Class<T> type,
        boolean needQuote) {
        if (inputStream == null || type == null || encode == null || StringUtils.isEmpty(separator)) {
            throw new IllegalArgumentException("输入参数不能为空");
        }

        char[] chars = separator.toCharArray();

        // 如果是单字符，则直接用单字符进行转换
        if (chars.length == 1) {
            return singleSeparatorToBeanList(inputStream, encode, chars[0], type, needQuote);

        } else {
            return customSeparatorToBeanList(inputStream, encode, separator, type, needQuote);

        }
    }

    /**
     * 对本地csv文件，进一步处理，避免后续进入工具处理时失败
     * 
     * @param srcCsvFile
     *            本地csv文件
     * @param encode
     *            原文件内容编码格式
     * @param srcString
     *            文件的分隔符
     * @return 在本地生成可以处理的csv文件，若原文件为空文件，则返回null
     */
    public static File toCanProcessCsvFile(File srcCsvFile, Charset encode, String srcString) {
        return toCanProcessFile(srcCsvFile, encode, srcString, DEFAULT_SEPARATOR_STR);
    }

    /**
     * 在{@link CsvToBeanUtil#TEMP_DIR}目录下，返回能处理的文件（可能是csv）,如果源文件是空文件，则返回null，根据原字符串替换为目标字符串
     * 
     * @param file
     *            本地文件
     * @param encode
     *            原文件内容编码格式
     * @param srcString
     *            文件的分隔符，或者任意字符串
     * @param targetString
     *            变成目标字符串
     * @return 在本地生成替换后的文件
     */
    public static File toCanProcessFile(File file, Charset encode, String srcString, String targetString) {
        if (file == null) {
            throw new IllegalArgumentException("输入参数不能为空");
        }

        try (InputStream inputStream = FileUtils.openInputStream(file)) {
            return toCanProcessFile(inputStream, encode, srcString, targetString);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 在getDirPath()目录下，返回能处理的文件（可能是csv）,如果源流是空，则返回null，根据原字符串替换为目标字符串
     *
     * @param inputStream
     *            本地或者网络文件流
     * @param encode
     *            原文件内容编码格式
     * @param srcString
     *            本地或者网络文件流的分隔符，或者任意字符串
     * @param targetString
     *            变成目标字符串
     * @return 在本地生成替换后的文件
     */
    public static File toCanProcessFile(InputStream inputStream, Charset encode, String srcString,
        String targetString) {
        if (inputStream == null || encode == null || StringUtils.isEmpty(srcString)
            || StringUtils.isEmpty(targetString)) {
            throw new IllegalArgumentException("输入参数不能为空");
        }

        File tempFile = getTempFile();

        // 标识文件是否为空
        boolean isNullFile = true;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encode));

            BufferedWriter tempWriter =
                new BufferedWriter(new OutputStreamWriter(FileUtils.openOutputStream(tempFile), encode))) {

            // 如果srcString和targetString一致则不编译，否则先编译好模式
            Pattern pattern = null;
            if (!targetString.equals(srcString)) {
                pattern = Pattern.compile(srcString, Pattern.LITERAL);

            }

            // 读取一行文件内容，暂存tempString
            String tempString;

            while ((tempString = bufferedReader.readLine()) != null) {
                if (StringUtils.isBlank(tempString)) {
                    continue;
                }

                if (isNullFile) {
                    isNullFile = false;
                }

                // 如果pattern不为null，将文件内容中的srcString更替为targetString
                if (pattern != null) {
                    tempString = pattern.matcher(tempString).replaceAll(Matcher.quoteReplacement(targetString));

                }

                // 写入临时文件
                tempWriter.append(tempString);
                tempWriter.newLine();
            }
            // 刷新缓存中的数据到临时文件
            tempWriter.flush();

            if (isNullFile) {
                return null;

            } else {
                return tempFile;

            }
        } catch (IOException e) {
            throw new RuntimeException(e);

        } finally {
            if (isNullFile) {
                // 如果为srcCsvFile空文件，则在tempFile删除
                FileUtils.deleteQuietly(tempFile);

            }
        }

    }

    /**
     * @see CsvToBeanUtil#toBeanIterator(InputStream, Charset, Class, boolean)
     */
    public static <T> Iterator<T> toBeanIterator(InputStream inputStream, Charset encode, Class<T> beanType) {
        return toBeanIterator(inputStream, encode, beanType, false);
    }

    /**
     * 使用迭代的方式必须关注流，需要自己记得去关闭，api内部不能关闭，因为关闭后，迭代时会出现IOException: Stream Closed;
     * 使用该api时，需要先用toCanProcessCsvFile()对原文件进行处理，在将生成的临时文件流放入，迭代完成后，记得关闭临时文件流，并删除临时文件
     * 
     * @param inputStream
     *            正确的csv流，经过需要先用toCanProcessCsvFile()处理
     * @param encode
     *            原文件编码格式
     * @param beanType
     *            转换的class
     * @param needQuote
     *            输入的csv文件中，字段是否有双引号括住
     * @return 该class的迭代器
     */
    public static <T> Iterator<T> toBeanIterator(InputStream inputStream, Charset encode, Class<T> beanType,
        boolean needQuote) {
        if (inputStream == null || encode == null || beanType == null) {
            throw new IllegalArgumentException("输入参数不能为空");
        }

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encode));

        char quoteChar = getQuoteChar(needQuote);

        return new CsvToBeanBuilder<T>(bufferedReader).withSeparator(DEFAULT_SEPARATOR_CHAR)
            .withEscapeChar(DEFAULT_ESCAPE_CHAR).withQuoteChar(quoteChar).withOrderedResults(true).withType(beanType)
            .build().iterator();

    }

    private static <T> List<T> toBeanList(InputStream inputStream, Charset encode, char separator, Class<T> beanType,
        boolean needQuote) {
        char quoteChar = getQuoteChar(needQuote);

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encode))) {

            return new CsvToBeanBuilder<T>(bufferedReader).withSeparator(separator).withEscapeChar(DEFAULT_ESCAPE_CHAR)
                .withQuoteChar(quoteChar).withOrderedResults(true).withType(beanType).build().parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> List<T> customSeparatorToBeanList(InputStream inputStream, Charset encode, String separator,
        Class<T> type, boolean needQuote) {
        // 在项目目录下创建tempTarget文件夹
        File tempFile = getTempFile();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encode));

            BufferedWriter tempWriter =
                new BufferedWriter(new OutputStreamWriter(FileUtils.openOutputStream(tempFile), encode));

            InputStream tempInputStream = FileUtils.openInputStream(tempFile)) {
            // 先编译好模式
            Pattern pattern = Pattern.compile(separator, Pattern.LITERAL);

            // 读取一行文件内容，暂存tempString
            String tempString;

            // 标识文件是否为空
            boolean isNullFile = true;
            while ((tempString = bufferedReader.readLine()) != null) {
                if (StringUtils.isBlank(tempString)) {
                    continue;
                }

                if (isNullFile) {
                    isNullFile = false;
                }

                // 将文件内容中的分隔符，更替为通用分隔符"\u0001"
                tempString = pattern.matcher(tempString).replaceAll(Matcher.quoteReplacement(DEFAULT_SEPARATOR_STR));

                // 写入临时文件
                tempWriter.append(tempString);
                tempWriter.newLine();
            }
            // 刷新缓存中的数据到临时文件
            tempWriter.flush();

            if (isNullFile) {
                return new ArrayList<>(0);
            } else {
                return toBeanList(tempInputStream, encode, DEFAULT_SEPARATOR_CHAR, type, needQuote);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 删除临时文件
            FileUtils.deleteQuietly(tempFile);

        }
    }

    private static <T> List<T> singleSeparatorToBeanList(InputStream inputStream, Charset encode, char separator,
        Class<T> type, boolean needQuote) {
        // 在项目目录下创建tempTarget文件夹
        // 去除多余行
        File tempFile = getTempFile();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encode));

            BufferedWriter tempWriter =
                new BufferedWriter(new OutputStreamWriter(FileUtils.openOutputStream(tempFile), encode));

            InputStream tempInputStream = FileUtils.openInputStream(tempFile)) {

            // 读取一行文件内容，暂存tempString
            String tempString;

            // 标识文件是否为空
            boolean isNullFile = true;
            while ((tempString = bufferedReader.readLine()) != null) {
                if (StringUtils.isBlank(tempString)) {
                    continue;
                }

                if (isNullFile) {
                    isNullFile = false;
                }

                // 写入临时文件
                tempWriter.append(tempString);
                tempWriter.newLine();
            }
            // 刷新缓存中的数据到临时文件
            tempWriter.flush();

            if (isNullFile) {
                return Collections.emptyList();
            } else {
                return toBeanList(tempInputStream, encode, separator, type, needQuote);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            // 删除临时文件
            FileUtils.deleteQuietly(tempFile);
        }
    }

    private static File getTempFile() {
        return FileUtils.getFile(new File(TEMP_DIR), UUID.randomUUID().toString());
    }

}
