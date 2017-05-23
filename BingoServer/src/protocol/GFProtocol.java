package protocol;

public class GFProtocol {
	
	private static final String RANKING_INFORMATION		= "RI/%s";
	private static final String LOGIN_ACTION			= "L/%s";
	private static final String LOGIN_RESPONSE			= "LR/%s";
	private static final String REGISTER_ACTION			= "NU/%s";
	private static final String REGISTER_RESPONSE		= "NUF/%s";
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
		
		if(packet.startsWith("L/") && packetLen > 2)
			return PacketType.LOGIN;
		
		if(packet.startsWith("LR/") && packetLen > 3)
			return PacketType.LOGIN_F;
		
		if(packet.startsWith("NU/") && packetLen > 3)
			return PacketType.REGISTER;
		
		if(packet.startsWith("NUF/") && packetLen > 4)
			return PacketType.REGISTER_F;
		
		if(packet.startsWith("CAT/") && packetLen > 4)
			return PacketType.CARTELA;
		
		if(packet.startsWith("BG/") && packetLen == 3)
			return PacketType.BINGO;
		
		if(packet.startsWith("BGF/") && packetLen == 5)
			return PacketType.BINGO_F;
		
		if(packet.startsWith("MN/") && packetLen > 3)
			return PacketType.NUMBER_CLICK;
		
		if(packet.startsWith("MNF/") && packetLen > 4)
			return PacketType.NUMBER_CLICK_F;
		
		if(packet.startsWith("SN/") && packetLen > 3)
			return PacketType.SORT_NUMBER;
		
		if(packet.startsWith("EG/") && packetLen > 3)
			return PacketType.END_GAME;
		
		return PacketType.NONE;
	}
	
	public static Ranking getRanking(String packet)
	{
		if(GFProtocol.getPacketType(packet) == PacketType.RANKING)
		{
			
		}
	}

}