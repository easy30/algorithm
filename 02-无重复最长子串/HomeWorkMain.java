
public class HomeWorkMain {

	public static void main(String[] args) {
		String str = "12345aaaabcddefggghiiiiijjjklmnnnnnopqrs";
		System.out.println(getMaxUniqueStrCount(str));
		System.out.println(getMaxUniqueStrCountV2(str));
	}
	
	public static int getMaxUniqueStrCount(String str) {
		if( str != null && str.length() == 1 ) {
			return 1;
		}
		byte[] strTem = str.getBytes();
		String strMax = "";
		int nIndex = 0;
		for( int i = 1; i < strTem.length; i++ ) {
			if( strTem[i-1] == strTem[i] ) {
				String strSub = str.substring(nIndex, i);
				if( strSub.length() > strMax.length())
					strMax = strSub;
				nIndex = i;
			}
		}
		if( nIndex < strTem.length - 1 ) {
			String strSub = str.substring(nIndex);
			if( strSub.length() > strMax.length() )
				strMax = strSub;
		}
		System.out.println(strMax);
		return strMax.length();
	}
	
	public static int getMaxUniqueStrCountV2(String str) {
		if( str != null && str.length() == 1 ) {
			return 1;
		}
		byte[] strTem = str.getBytes();
		int nMaxLen = 0;
		int nIndex = 0;
		for( int i = 1; i < strTem.length; i++ ) {
			if( strTem[i-1] == strTem[i] ) {
				if( i - nIndex > nMaxLen)
					nMaxLen = i-nIndex;
				nIndex = i;
			}
		}
		if( nIndex < strTem.length - 1 ) {
			if( strTem.length - nIndex > nMaxLen)
				nMaxLen = strTem.length-nIndex;
		}
		return nMaxLen;
	}
}
