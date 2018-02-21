package com.srct.ril.poas.ai.baidunlp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.srct.ril.poas.utils.Log;

public class BaiduNLPDepParser {
	
	
	private final static String ATT = "ATT"; // 定中关系就是定语和中心词之间的关系，定语对中心词起修饰或限制作用。
	private final static String QUN = "QUN"; // 数量关系是指量词或名词同前面的数词之间的关系，该关系中，数词作修饰成分，依存于量词或名词。
	private final static String COO = "COO"; // 并列关系是指两个相同类型的词并列在一起。
	private final static String APP = "APP"; // 同位语是指所指相同、句法功能也相同的两个并列的词或词组。 
	private final static String ADJ = "ADJ"; // 附加关系是一些附属词语对名词等成分的一种补充说明，使意思更加完整，有时候去掉也不影响意思
	private final static String VOB = "VOB"; // 对于动词和宾语之间的关系我们定义了两个层次，一是句子的谓语动词及其宾语之间的关系，我们定为OBJ，在下面的单句依存关系中说明；二是非谓语动词及其宾语的关系，即VOB。这两种关系在结构上没有区别，只是在语法功能上，OBJ中的两个词充当句子的谓语动词和宾语，VOB中的两个词构成动宾短语，作为句子的其他修饰成分。
	private final static String POB = "POB"; // 介词和宾语之间的关系，介词的属性同动词相似。
	private final static String SBV = "SBV"; // 主谓关系是指名词和动作之间的关系。
	private final static String SIM = "SIM"; // 比拟关系是汉语中用于表达比喻的一种修辞结构。
	private final static String TMP = "TMP"; // 时间关系定义的是时间状语和其所修饰的中心动词之间的关系。
	private final static String LOC = "LOC"; // 处所关系定义的是处所状语和其所修饰的中心动词之间的关系
	private final static String DE = "DE"; // “的”字结构是指结构助词“的”和其前面的修饰语以及后面的中心词之间的关系。
	private final static String DI= "DI"; // “地”字结构在构成上同DE类似，只是在功能上不同，DI通常作状语修饰动词。
	private final static String DEI= "DEI"; // 助词“得”同其后的形容词或动词短语等构成“得”字结构，对前面的动词进行补充说明。
	private final static String SUO = "SUO"; // “所”字为一结构助词，后接一宾语悬空的动词做“的”字结构的修饰语，“的”字经常被省略，使结构更加简洁。
	private final static String BA = "BA"; // 把字句是主谓句的一种，句中谓语一般都是及物动词。
	private final static String BEI = "BEI"; // 被字句是被动句，是主语接受动作的句子。
	private final static String ADV = "ADV"; // 状中结构是谓词性的中心词和其前面的修饰语之间的关系，中心词做谓语时，前面的修饰成分即为句子的状语，中心词多为动词、形容词，修饰语多为副词，介词短语等。
	private final static String CMP = "CMP"; // 补语用于对核心动词的补充说明。
	private final static String DBL = "DBL"; // 兼语句一般有两个动词，第二个动词是第一个动作所要表达的目的或产生的结果。
	private final static String CNJ = "CNJ"; // 关联词语是复句的有机部分。
	private final static String CS = "CS"; // 当句子中存在关联结构时，关联词所在的两个句子（或者两个部分）之间通过各部分的核心词发生依存关系CS。
	private final static String MT = "MT"; // 汉语中，经常用一些助词表达句子的时态和语气，这些助词分语气助词，如：吧，啊，呢等；还有时态助词，如：着，了，过。
	private final static String VV = "VV"; // 连谓结构是同多项谓词性成分连用、这些成分间没有语音停顿、书面标点，也没有关联词语，没有分句间的逻辑关系，且共用一个主语。
	private final static String HED = "HED"; // 该核心是指整个句子的核心，一般是句子的核心词和虚拟词（<EOS>或ROOT）的依存关系。
	private final static String FOB = "FOB"; // 在汉语中，有时将句子的宾语前置，或移置句首，或移置主语和谓语之间，以起强调作用，我认识这个人 ← 这个人我认识。
	private final static String DOB = "DOB"; // 动词后出现两个宾语的句子叫双宾语句，分别是直接宾语和间接宾语。
	private final static String TOP = "TOP"; // 在表达中，我们经常会先提出一个主题性的内容，然后对其进行阐述说明；而主题部分与后面的说明部分并没有直接的语法关系，主题部分依存于后面的核心成分，且依存关系为TOP。
	private final static String IS = "IS"; // 独立成分在句子中不与其他成分产生结构关系，但意义上又是全句所必需的，具有相对独立性的一种成分。
	private final static String IC = "IC"; // 两个单句在结构上彼此独立，都有各自的主语和谓语。
	private final static String DC = "DC"; // 两个单句在结构上不是各自独立的，后一个分句的主语在形式上被省略，但不是前一个分句的主语，而是存在于前一个分句的其他成分中，如宾语、主题等成分。规定后一个分句的核心词依存于前一个分句的核心词。该关系同连谓结构的区别是两个谓词是否为同一主语，如为同一主语，则为VV，否则为DC。
	private final static String VNV = "VNV"; // 如果叠词被分开了，如“是 不 是”、“看一看”，那么这几个词先合并在一起，然后预存到其他词上，叠词的内部关系定义为：(是1→不；不→是2） 。
	private final static String YGC = "YGC"; // 当专名或者联绵词等切散后，他们之间本身没有语法关系，应该合起来才是一个词。如：百 度。
	private final static String WP = "WP"; // 大部分标点依存于其前面句子的核心词上，依存关系WP。
	
	private final static String[] ingoreTable = {CS,IS,IC,DC}; // 该成分不连在一个分句中
	private final static String[] meaningfulTable = {HED, SBV, VOB}; // 若包含该成分则保留该分句
	private Queue<Integer> queue = new LinkedList<Integer>();
	
	private List<Integer> forestRoot = new ArrayList<Integer>(); // 分句根节点
	private Map<Integer, List<Integer>> treeMap = new HashMap<Integer, List<Integer>>(); // 分句Item树

	public List<String> simpleText = new ArrayList<String>(); // 取舍关键分句后 保留分句

	public long log_id;
	public String text; // 原始单条请求文本
	public Items[] items; // 词汇数组，每个元素对应结果中的一个词
	
	public static class Items {
		public int id; // 	词的ID
		public String word;   // 词
		public String postag;  // 词性，请参照API文档中的词性（postag)取值范围
		public int head; // 词的父节点ID
		public String  deprel; // 词与父节点的依存关系，请参照API文档的依存关系标识
		public int treeRoot = -1;
		public List<Integer> child = new ArrayList<Integer>(); 
	}
	
	public List<String> parse() {
		makeTree();
		makeTreeMap();
		makeSimpleText();
		return simpleText;
		
	}
	
	private boolean meaningfulItem(Items it) {
		for(String meaningful: meaningfulTable)
			if(it.deprel.equals(meaningful))
				return true;
		return false;
	}
	
	private Boolean meaningGroupItem(Items it) {
		for(String ignore: ingoreTable)
			if(it.deprel.equals(ignore))
				return false;
		return true;
	}
	
	private void setTreeRoot(int root) {
		items[root].treeRoot=root;
		queue.offer(root);
		while(queue.size()!=0) {
			int index = queue.poll();
			for(Integer chi : items[index].child) {
				items[chi].treeRoot=root;
				queue.offer(chi);			
			}
		}
	}
	
	private void makeTree() {
		// make tree
		// 构建分句间树形结构 HEAD IS IC
		for(int i=0;i<items.length;i++) {
			items[i].id = i;
			items[i].head--;
			if(items[i].head==-1) {
				forestRoot.add(i);
			} else {
				if(meaningGroupItem(items[i]))
					items[items[i].head].child.add(i);
				else {
					if(!items[items[i].head].deprel.equals(HED))
						items[items[i].head].child.add(i);
					else
						forestRoot.add(i);
				}
			}
		}

		if(forestRoot.size()==0) {
			Log.i(getClass(), "there is no HED");
		} else {
			for(Integer root : forestRoot) {
				setTreeRoot(root);
			}
		}
		
	}
	private void makeSimpleText() {
		
		for (Map.Entry<Integer, List<Integer>> entry : treeMap.entrySet()) {
			boolean meaningfulContent = false;
			String text="";
			for(Integer i : entry.getValue()) {
				if(meaningfulItem(items[i])) {
					meaningfulContent = true;
				}
			}
			if(meaningfulContent) {
				Collections.sort(entry.getValue());
				for(Integer i : entry.getValue()) {
					text+=items[i].word;
				}
				simpleText.add(text);
			}
		}
		
	}
	
	private void makeTreeMap() {
		Map<Integer, List<Integer>> tempTreeMap = new HashMap<Integer, List<Integer>>(); // 分句Item树
		for(Integer root :forestRoot) {
			treeMap.put(root, new ArrayList<Integer>());
			tempTreeMap.put(root, new ArrayList<Integer>());
		}
		
		for(Items it : items) {
			tempTreeMap.get(it.treeRoot).add(it.id);
		}
		
//		// 分句若无核心词则关联分句归并
//		for (Map.Entry<Integer, List<Integer>> entry : tempTreeMap.entrySet()) {
//			boolean meaningfulContent = false;
//			for(Integer i : entry.getValue()) {
//				if(meaningfulItem(items[i])) {
//					meaningfulContent = true;
//					break;
//				}
//			}
//			if(meaningfulContent == false) {
//				Integer index = entry.getKey();
//				if()
//				forestRoot.remove(index);
//				items[items[index].head].child.add(index);
//				setTreeRoot(items[index].head);
//			}	
//		}
		
		for(Items it : items) {
			treeMap.get(it.treeRoot).add(it.id);
		}
	}
	
}
