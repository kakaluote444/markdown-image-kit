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

package info.dong4j.idea.plugin.chain;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.progress.ProgressIndicator;

import info.dong4j.idea.plugin.entity.EventData;
import info.dong4j.idea.plugin.entity.MarkdownImage;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>Company: 科大讯飞股份有限公司-四川分公司</p>
 * <p>Description: 替换原有标签</p>
 *
 * @author dong4j
 * @email sjdong3@iflytek.com
 * @since 2019-03-28 13:49
 */
public class ReplaceToDocument extends ActionHandlerAdapter {
    @Override
    public String getName() {
        return "替换原有标签";
    }

    @Override
    public boolean execute(EventData data) {
        ProgressIndicator indicator = data.getIndicator();
        int size = data.getSize();
        int totalProcessed = 0;

        for (Map.Entry<Document, List<MarkdownImage>> imageEntry : data.getWaitingProcessMap().entrySet()) {
            Document document = imageEntry.getKey();
            int totalCount = imageEntry.getValue().size();
            for (MarkdownImage markdownImage : imageEntry.getValue()) {
                String imageName = markdownImage.getImageName();
                indicator.setFraction(((++totalProcessed * 1.0) + data.getIndex() * size) / totalCount * size);
                indicator.setText2("Processing " + imageName);

                String finalMark = markdownImage.getFinalMark();
                if(StringUtils.isBlank(finalMark)){
                    continue;
                }
                String newLineText = markdownImage.getOriginalLineText().replace(markdownImage.getOriginalMark(), finalMark);

                WriteCommandAction.runWriteCommandAction(data.getProject(), () -> document
                    .replaceString(document.getLineStartOffset(markdownImage.getLineNumber()),
                                   document.getLineEndOffset(markdownImage.getLineNumber()),
                                   newLineText));



            }
        }
        return true;
    }
}
