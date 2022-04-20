package com.brookezb.bhs.utils;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.dfa.WordTree;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author brooke_zb
 */
@Service
@Slf4j
public class CensorWordUtils {
    private final WordTree wordTree;

    public CensorWordUtils(@Value("${censor.path}") String wordPath) throws NoSuchFieldException {
        if (wordPath != null) {
            String[] words = new FileReader(wordPath).readString().split("\n");
            wordTree = new WordTree();
            for (String word : words) {
                wordTree.addWord(word);
            }
            log.info("敏感词库加载完成");
        } else {
            throw new NoSuchFieldException("敏感词库加载失败");
        }
    }

    public boolean isMatch(String content) {
        return wordTree.isMatch(content);
    }
}
