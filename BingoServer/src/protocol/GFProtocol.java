package protocol;

public class GFProtocol {
	
	private static final String RANKING_INFORMATION		= "RI/%s";
	private static final String LOGIN_ACTION			= "L/%s";
	private static final String LOGIN_RESPONSE			= "LR/%s";
	private static final String REGISTER_ACTION			= "NU/%s";
	private static final String REGISTER_RESPONSE		= "NUF/";
	private static final String CARTELA					= "CAT/%s";
	private static final String BINGO					= "BG/";
	private static final String BINGO_RESPONSE			= "BGF/%s";
	private static final String NUMBER_CLICK			= "MN/%d";
	private static final String NUMBER_CLICK_RESPONSE	= "MNF/%s";
	private static final String SORT_NUMBER				= "SN/%d";
	private static final String END_GAME				= "EG/%s";
	
	static class PacketType {
		static final int NONE			= 0;
		static final int RANKING		= 1;
		static final int LOGIN			= 2;
		static final int LOGIN_F		= 3;
		static final int REGISTER		= 4;
		static final int REGISTER_F		= 5;
		static final int CARTELA		= 6;
		static final int BINGO			= 7;
		static final int BINGO_F		= 8;
		static final int NUMBER_CLICK	= 9;
		static final int NUMBER_CLICK_F	= 10;
		static final int SORT_NUMBER	= 11;
		static final int END_GAME		= 12;
	}
	
	public static int getPacketType(String packet)
	{
		int packetLen = packet.length();
		
		if(packet.startsWith("RI/") && packetLen > 3)
			return PacketType.RANKING;
		
		return PacketType.NONE;
	}
	
	public static Ranking getRanking(String packet)
	{
		return null;
	}

}
