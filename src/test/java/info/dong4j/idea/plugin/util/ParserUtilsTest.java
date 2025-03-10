package info.dong4j.idea.plugin.util;

import info.dong4j.idea.plugin.content.ImageContents;
import info.dong4j.idea.plugin.enums.ImageMarkEnum;

import org.junit.Test;

import java.io.*;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Company: 科大讯飞股份有限公司-四川分公司</p>
 * <p>Description: ${description}</p>
 *
 * @author dong4j
 * @date 2019-03-12 22:40
 * @email sjdong3@iflytek.com
 */
@Slf4j
public class ParserUtilsTest {
    @Test
    public void test() {
        Map<String, String> map = ParserUtils.parseImageTag("![xxx](yyy)");
        for (Map.Entry<String, String> result : map.entrySet()) {
            log.info("key = {}, value = {}", result.getKey(), result.getValue());
        }

        String filePath = "./imgs/1eefcf26.png";
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
        log.info(fileName);

        String url = "http://qiniu.dong4j.info/2019-03-27-MwoAVW.png";
        String imageName = url.substring(url.lastIndexOf("/") + 1);
        log.info(imageName);
    }

    @Test
    public void test1() {
        String str = "<a data-fancybox title='' href='https://dong4j-imgs.oss-cn-hangzhou.aliyuncs.com/blog/JavaKnowledgePoint.png' >![](https://dong4j-imgs.oss-cn-hangzhou.aliyuncs.com/blog/JavaKnowledgePoint.png)</a>";

        log.info("title = {}, path = {}", str.substring(str.indexOf(ImageContents.IMAGE_MARK_PREFIX) + ImageContents.IMAGE_MARK_PREFIX.length(),
                                                        str.indexOf(ImageContents.IMAGE_MARK_MIDDLE)),
                 str.substring(str.indexOf(ImageContents.IMAGE_MARK_MIDDLE) + ImageContents.IMAGE_MARK_MIDDLE.length(),
                               str.indexOf(ImageContents.IMAGE_MARK_SUFFIX)));

        log.info("{}", str.indexOf(ImageContents.HTML_TAG_A_START));
        log.info("{}", str.indexOf(ImageContents.HTML_TAG_A_END) + 2);
    }

    @Test
    public void test2(){
        String path = "./xxx/aa.png";
        String imageName = path.substring(path.lastIndexOf(File.separator) + 1);
        log.info("imagename = {}", imageName);
    }

    @Test
    public void test3(){
        log.info(System.getProperty("java.io.tmpdir"));
    }

    @Test
    public void test4(){
        String title = "aaa";
        String path = "./imgs/xxx.png";
        log.trace("{}", ParserUtils.parse2(ImageMarkEnum.LARGE_PICTURE.code, title, path));
    }

    @Test
    public void test5(){
        String str = "2019-11-11-2019-11-11-xxxx.png";
        log.info("{}", str.replace("2019-11-11-", ""));
    }
}