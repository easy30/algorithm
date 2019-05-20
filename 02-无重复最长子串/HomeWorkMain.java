
public class HomeWorkMain {

	public static void main(String[] args) {
		String str = "abcdddefghiiiiijjjklmnopqrst";
		System.out.println(getMaxUniqueStrCountV1(str));
	}
	
	public static int getMaxUniqueStrCountV1(String str) {
		if( str != null && str.length() == 1 ) {
			return 1;
		}
		char[] strTem = str.toCharArray();
		String strMax = "";
		int nMaxLen = 0;
		for( int i = 0; i < strTem.length; i++ ) {
			if( strMax.indexOf(strTem[i]) > -1 ) {
				nMaxLen = strMax.length() > nMaxLen ? strMax.length() : nMaxLen;
				strMax = ""+strTem[i];
			} else {
				strMax += strTem[i];
			}
		}
		if( strMax.length() > nMaxLen ) {
			nMaxLen = strMax.length();
		}
		return nMaxLen;
	}
}

