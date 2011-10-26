/*
 * Copyright 2010 Alibaba Group Holding Limited.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.antx.config.entry;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.alibaba.antx.config.ConfigException;
import com.alibaba.antx.config.ConfigResource;
import com.alibaba.antx.config.ConfigSettings;
import com.alibaba.antx.config.generator.ConfigGeneratorSession;
import com.alibaba.antx.config.generator.DirectoryCallback;
import com.alibaba.antx.util.scanner.DirectoryScanner;
import com.alibaba.antx.util.scanner.Scanner;
import com.alibaba.antx.util.scanner.ScannerException;

/**
 * ����һ��Ŀ¼���͵���������Ϣ��
 * 
 * @author Michael Zhou
 */
public class DirectoryConfigEntry extends ConfigEntry {
    /**
     * ����һ����㡣
     * 
     * @param resource ָ��������Դ
     * @param settings antxconfig������
     */
    public DirectoryConfigEntry(ConfigResource resource, File outputFile, ConfigSettings settings) {
        super(resource, outputFile, settings);
    }

    /**
     * ɨ���㡣
     */
    protected void scan(InputStream istream) {
        Handler handler = new Handler();
        Scanner scanner = new DirectoryScanner(getConfigEntryResource().getFile(), handler);

        try {
            scanner.scan();
        } catch (ScannerException e) {
            throw new ConfigException(e);
        }

        subEntries = handler.getSubEntries();

        getGenerator().init();
    }

    /**
     * ���������ļ���
     */
    protected boolean generate(InputStream istream, OutputStream ostream) {
        getConfigSettings().debug("Processing files in " + getConfigEntryResource().getURL());

        boolean allSuccess = true;

        // �����Լ���descriptors
        try {
            ConfigGeneratorSession session = getGenerator().startSession(getConfigSettings().getPropertiesSet());

            allSuccess &= session.generate(new DirectoryCallback(getGenerator(), getOutputFile()));
        } finally {
            getGenerator().closeSession();
        }

        // ������entries
        ConfigEntry[] subEntries = getSubEntries();

        for (int i = 0; i < subEntries.length; i++) {
            ConfigEntry subEntry = subEntries[i];

            allSuccess &= subEntry.generate(null, null);
        }

        return allSuccess;
    }

    /**
     * ת�����ַ�����
     * 
     * @return �ַ�����ʾ
     */
    public String toString() {
        return "DirectoryConfigEntry[" + getConfigEntryResource() + "]";
    }
}
