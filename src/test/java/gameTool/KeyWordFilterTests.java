package gameTool;

import org.junit.Test;

import com.hahazql.util.wordsfilter.KeyWordsACFilter;

public class KeyWordFilterTests {
	@Test
	public void testFilter()
	{
		KeyWordsACFilter filter = KeyWordsACFilter.getInstance('*',"cccc");
		String out = filter.filt("ccccc");
		System.out.println(out);
	}
}
