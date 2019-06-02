package cj.netos.boudbank.plugin.BDBKEngine.util;

import java.math.RoundingMode;

public interface BigDecimalConstants {
	static String bondKind = "TY";
	static String currency = "CNY";
	static int scale = 16;// 小数位数为16
	static RoundingMode roundingMode = RoundingMode.FLOOR;
	static float ultimate_BondPrice = 0.001F;
	static float bondRate = 0.7F;
	static float reserveRate = 0.1F;
	static float freeRate = 0.2F;
}
