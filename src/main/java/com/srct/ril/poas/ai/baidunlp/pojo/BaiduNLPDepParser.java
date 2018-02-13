package com.srct.ril.poas.ai.baidunlp.pojo;

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
	
	
	public long log_id;
	public String text; // 原始单条请求文本
	public Items[] items; // 词汇数组，每个元素对应结果中的一个词
	
	public static class Items {
		public int id; // 	词的ID
		public String word;   // 词
		public String postag;  // 词性，请参照API文档中的词性（postag)取值范围
		public int head; // 词的父节点ID
		public String  deprel; // 词与父节点的依存关系，请参照API文档的依存关系标识
		public boolean meanful;
	}
	
	public String parse() {
		String res=null;
		for(int i=0;i<items.length;i++) {
			items[i].id = i+1;
		}
		
		return res;
		
	}
	
}
