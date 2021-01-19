/**
 * IK 中文分词  版本 5.0.1
 * IK Analyzer release 5.0.1
 * <p>
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * 源代码由林良益(linliangyi2005@gmail.com)提供
 * 版权声明 2012，乌龙茶工作室
 * provided by Linliangyi and copyright 2012 by Oolong studio
 */
package org.wltea.analyzer.sample;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.junit.Test;
import org.wltea.analyzer.dic.Dictionary;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;
import java.util.stream.IntStream;

/**
 * 使用IKAnalyzer进行分词的演示
 * 2012-10-22
 */
public class IKAnalzyerTest {

    @Test
    public void testAnalyzer() {
        //构建IK分词器，使用smart分词模式
        Analyzer analyzer = new IKAnalyzer(true);
        //获取Lucene的TokenStream对象
        TokenStream ts = null;
        String msg = "这是一个中文分词的例子，好大夫在线A股你可以直接运行它！ 找我靠我来搞，我靠你怎么能这样，我靠wechat wxin IKAnalyer can analysis english text too";
        StringBuilder sb = new StringBuilder(msg);
        try {
            ts = analyzer.tokenStream("myfield",
                    new StringReader(msg));
            //获取词元位置属性
            OffsetAttribute offset = ts.addAttribute(OffsetAttribute.class);
            //获取词元文本属性
            CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
            //获取词元文本属性
            TypeAttribute type = ts.addAttribute(TypeAttribute.class);

            //重置TokenStream（重置StringReader）
            ts.reset();
            //迭代获取分词结果
            while (ts.incrementToken()) {
                final String dicName = Dictionary.getSingleton().getDicName(term.toString());
                if ("hide".equals(dicName)) {
                    sb.replace(offset.startOffset(), offset.endOffset(), hideChar(offset.endOffset() - offset.startOffset()));
                } else if ("forbid".equals(dicName)) {
//                    throw new ForbiddenException("");
                }
                System.out.println(
                        offset.startOffset() + " - " + offset.endOffset() + " : " + term.toString() + " | " + type
                                .type() + " |");
            }
            ts.end();   // Perform end-of-stream operations, e.g. set the final offset.
            System.out.println(sb);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //释放TokenStream的所有资源
            if (ts != null) {
                try {
                    ts.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private String hideChar(int i) {
        StringBuilder hide = new StringBuilder();
        IntStream.range(0, i).forEach(n -> {
            hide.append('*');
        });
        return hide.toString();
    }

}
