/*
 * MIT License
 *
 * Copyright (c) 2019 dong4j <dong4j@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package info.dong4j.idea.plugin.client;

import com.google.gson.Gson;

import info.dong4j.idea.plugin.entity.SmmsResult;
import info.dong4j.idea.plugin.enums.CloudEnum;

import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Contract;

import java.io.*;

import javax.swing.JPanel;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * <p>Company: 科大讯飞股份有限公司-四川分公司</p>
 * <p>Description: </p>
 *
 * @author dong4j
 * @email sjdong3 @iflytek.com
 * @since 2019 -04-01 09:21
 */
@Slf4j
@Client(CloudEnum.SM_MS_CLOUD)
public class SmmsClient implements OssClient {
    private static final String UPLOAD_URL = "https://sm.ms/api/upload";
    private Client ossClient;

    private SmmsClient() {

    }

    @Override
    public CloudEnum getCloudType() {
        return CloudEnum.SM_MS_CLOUD;
    }

    /**
     * Upload string.
     *
     * @param inputStream the input stream
     * @param fileName    the file name
     * @return the string
     */
    @Override
    public String upload(InputStream inputStream, String fileName) {
        return upload(ossClient, inputStream, fileName);
    }

    /**
     * sm.ms 不需要测试
     * {@link info.dong4j.idea.plugin.settings.ProjectSettingsPage#testAndHelpListener()}
     *
     * @param inputStream the input stream
     * @param fileName    the file name
     * @param jPanel      the j panel
     * @return the string
     */
    @Override
    public String upload(InputStream inputStream, String fileName, JPanel jPanel) {
        return "";
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    @Contract(pure = true)
    public static SmmsClient getInstance() {
        return SmmsClient.SingletonHandler.singleton;
    }

    /**
     * Upload string.
     *
     * @param ossClient   the oss client
     * @param inputStream the input stream
     * @param fileName    the file name
     * @return the string
     */
    public String upload(Client ossClient, InputStream inputStream, String fileName) {
        if (ossClient == null) {
            ossClient = new Client();
        }
        return ossClient.upload(inputStream, fileName);
    }

    private static class SingletonHandler {
        private static SmmsClient singleton = new SmmsClient();

        static {

        }
    }

    private class Client {
        private OkHttpClient client;
        private Request request;

        /**
         * Instantiates a new Client.
         *
         * @throws IOException the io exception
         */
        Client() {
            this.client = new OkHttpClient();
        }

        /**
         * Upload string.
         *
         * @param inputStream the input stream
         * @param fileName    the file name
         * @return the string
         */
        public String upload(InputStream inputStream, String fileName) {
            try {
                RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("smfile", fileName, RequestBody.create(MediaType.parse("multipart/form-data"), IOUtils.toByteArray(inputStream)))
                    .build();

                this.request = new Request.Builder()
                    .url(UPLOAD_URL)
                    .addHeader("Content-Type", "multipart/form-data")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)")
                    .post(requestBody)
                    .build();
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    return "";
                }
                if (response.body() != null) {
                    String result = response.body().string();
                    SmmsResult smmsResult = new Gson().fromJson(result, SmmsResult.class);
                    log.trace("{}", smmsResult);
                    return smmsResult.getData().getUrl();
                }
            } catch (Exception e) {
                log.trace("", e);
            }
            return "";
        }
    }
}
