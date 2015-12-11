package com.haier.bean;

import java.util.Collections;
import java.util.List;

/**
 * 解析结果类
 * @author SYZ
 */
public class Result {
	public AccessFlag accessFlag = new AccessFlag();
    public List<KnowledgeInfo> knowledgeDBInfos = Collections.emptyList();	// 资料列表
    public List<QuestionInfo> firstInfos = Collections.emptyList();			// 问题分类列表
    public List<AskallInfos> askAllInfos = Collections.emptyList();			// 查询单个问大家帖子
    public int totalpage;
    
    public boolean OK() {
    	return "6".equals(accessFlag.getFlag());
    }
    public String message() {
    	return accessFlag.getDescribe();
    }
}
