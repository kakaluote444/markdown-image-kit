/*
 * MIT License
 *
 * Copyright (c) 2019 dong4j
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

package info.dong4j.idea.plugin.task;

import com.intellij.openapi.externalSystem.task.TaskCallback;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;

import info.dong4j.idea.plugin.chain.ActionManager;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>Company: 科大讯飞股份有限公司-四川分公司</p>
 * <p>Description: </p>
 *
 * @author dong4j
 * @email sjdong3 @iflytek.com
 * @since 2019-03-27 23:03
 */
@Slf4j
public abstract class MikTaskBase extends Task.Backgroundable{

    /**
     * Instantiates a new Mik task base.
     *
     * @param project the project
     * @param title   the title
     */
    private ActionManager manager;

    MikTaskBase(@Nullable Project project,
                @Nls(capitalization = Nls.Capitalization.Title) @NotNull String title,
                ActionManager manager) {
        super(project, title);
        this.manager = manager;
    }


    @Override
    public void run(@NotNull ProgressIndicator indicator) {
        indicator.pushState();
        indicator.setIndeterminate(false);
        try {
            indicator.setFraction(0.0);
            manager.invoke(indicator);
        } finally {
            indicator.setFraction(1.0);
            indicator.popState();
        }
    }


    @Override
    public void onCancel() {
        log.trace("cancel callback");
    }

    @Override
    public void onSuccess() {
        log.trace("success callback");
        for(TaskCallback callback : manager.getCallbacks()){
            callback.onSuccess();
        }
    }

    @Override
    public void onFinished() {
        log.trace("finished callback");
    }

    @Override
    public void onThrowable(@NotNull Throwable throwable) {
        super.onThrowable(throwable);
        log.trace("", throwable);
    }
}
