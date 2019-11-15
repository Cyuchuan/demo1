package com.cyc.demo1.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.opencsv.ICSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

/**
 * 配合@CsvBindByName、@CsvBindByPosition等注解使用
 * 
 * @author chenyuchuan
 */
public class CsvToBeanUtil {
    public static final String DEFAULT_SEPARATOR_STR = "\u0001";
    private static final Logger log = LoggerFactory.getLogger(CsvToBeanUtil.class);
    private static final char DEFAULT_SEPARATOR_CHAR = '\u0001';

    private CsvToBeanUtil() {

    }

    @SuppressWarnings("unchecked")
    public static <T> void writeBeansToFile(List<T> bean, File destFile, Charset encode, String separator) {
        File tempFile = getTempFile();

        File correctFile = null;
        try (FileOutputStream tempFileOutputStream = FileUtils.openOutputStream(tempFile);
            BufferedWriter tempWriter = new BufferedWriter(new OutputStreamWriter(tempFileOutputStream, encode));
            FileInputStream tempFileInputStream = FileUtils.openInputStream(tempFile)) {

            new StatefulBeanToCsvBuilder(tempWriter).withLineEnd(System.lineSeparator())
                .withQuotechar(ICSVWriter.NO_QUOTE_CHARACTER).withOrderedResults(false)
                .withSeparator(DEFAULT_SEPARATOR_CHAR).build().write(bean);
            tempWriter.flush();

            correctFile = toCanProcessFile(tempFileInputStream, encode, DEFAULT_SEPARATOR_STR, separator);
            if (correctFile != null) {
                FileUtils.copyFile(correctFile, destFile);

            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            FileUtils.deleteQuietly(tempFile);
            FileUtils.deleteQuietly(correctFile);

        }
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
    public static <T> List<T> toBeanList(File srcFile, Charset encode, String separator, Class<T> type) {
        try (FileInputStream fileInputStream = FileUtils.openInputStream(srcFile)) {
            return toBeanList(fileInputStream, encode, separator, type);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 一次性读出文件 使用注解@CsvBindByName（根据第一行的行头名称进行映射）或者@CsvBindByPosition（根据字段的位置进行映射，从0开始）标识class的字段
     * 
     * @param inputStream
     *            csv文件流，或者网络文件流
     * @param encode
     *            文件的编码格式
     * @param separator
     *            分隔符
     * @param type
     *            要转换的class
     * @return 对应文件的list列表
     */
    public static <T> List<T> toBeanList(InputStream inputStream, Charset encode, String separator, Class<T> type) {
        Assert.notNull(inputStream, "inputStream不能为空");
        Assert.notNull(type, "type不能为空");
        Assert.notNull(separator, "separator不能为空");
        Assert.notNull(encode, "encode不能为空");

        char[] chars = separator.toCharArray();
        // 如果是单字符，则直接用单字符进行转换
        if (chars.length == 1) {
            return singleSeparatorToBeanList(inputStream, encode, chars[0], type);

        } else {
            return customSeparatorToBeanList(inputStream, encode, separator, type);

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
     * 在getDirPath()目录下，返回能处理的文件（可能是csv）,如果源文件是空文件，则返回null，根据原字符串替换为目标字符串
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
        Assert.notNull(file, "file不能为null");
        Assert.notNull(encode, "encode不能为null");
        Assert.notNull(srcString, "srcString不能为null");
        Assert.notNull(targetString, "targetString不能为null");

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
        Assert.notNull(inputStream, "inputStream不能为null");
        Assert.notNull(encode, "encode不能为null");
        Assert.notNull(srcString, "srcString不能为null");
        Assert.notNull(targetString, "targetString不能为null");

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
     * 使用迭代的方式必须关注流，需要自己记得去关闭，api内部不能关闭，因为关闭后，迭代时会出现IOException: Stream Closed;
     * 使用该api时，需要先用toCanProcessCsvFile()对原文件进行处理，在将生成的临时文件流放入，迭代完成后，记得关闭临时文件流，并删除临时文件
     * 
     * @param inputStream
     *            正确的csv流，经过需要先用toCanProcessCsvFile()处理
     * @param encode
     *            原文件编码格式
     * @param beanType
     *            转换的class
     * @return 该class的迭代器
     */
    public static <T> Iterator<T> toBeanIterator(InputStream inputStream, Charset encode, Class<T> beanType) {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encode));

        return new CsvToBeanBuilder<T>(bufferedReader).withSeparator(DEFAULT_SEPARATOR_CHAR).withOrderedResults(false)
            .withType(beanType).build().iterator();

    }

    private static <T> List<T> toBeanList(InputStream inputStream, Charset encode, char separator, Class<T> beanType) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encode))) {

            return new CsvToBeanBuilder<T>(bufferedReader).withSeparator(separator).withOrderedResults(false)
                .withType(beanType).build().parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> List<T> customSeparatorToBeanList(InputStream inputStream, Charset encode, String separator,
        Class<T> type) {
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
                return toBeanList(tempInputStream, encode, DEFAULT_SEPARATOR_CHAR, type);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        } finally {
            // 删除临时文件
            FileUtils.deleteQuietly(tempFile);

        }
    }

    private static <T> List<T> singleSeparatorToBeanList(InputStream inputStream, Charset encode, char separator,
        Class<T> type) {
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
                return toBeanList(tempInputStream, encode, separator, type);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        } finally {
            // 删除临时文件
            FileUtils.deleteQuietly(tempFile);
        }
    }

    private static File getTempFile() {
        return FileUtils.getFile(new File("tempCsvBeanUtilTarget"), UUID.randomUUID().toString());
    }

}
