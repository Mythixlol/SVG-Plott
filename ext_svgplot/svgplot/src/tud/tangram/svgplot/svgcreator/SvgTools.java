package tud.tangram.svgplot.svgcreator;

import java.text.MessageFormat;
import java.util.EnumSet;

import tud.tangram.svgplot.Constants;
import tud.tangram.svgplot.coordinatesystem.CoordinateSystem;
import tud.tangram.svgplot.coordinatesystem.Point;
import tud.tangram.svgplot.options.OutputDevice;

public class SvgTools {
	/**
	 * Format a number for svg usage according to the constant decimalFormat
	 * 
	 * @param value
	 * @return
	 */
	public static String format2svg(double value) {
		return Constants.decimalFormat.format(value);
	}

	/**
	 * Formats an additional Name of an object. Checks if the name is set. If
	 * name is set, the name is packed into brackets and prepend with an
	 * whitespace
	 * 
	 * @param name
	 *            | optional name of an object or NULL
	 * @return empty string or the name of the object packed into brackets and
	 *         prepend with a whitespace e.g. ' (optional name)'
	 */
	public static String formatName(String name) {
		return (name == null || name.isEmpty()) ? "" : " (" + name + ")";
	}

	/**
	 * Try to translate a key in the localized version defined in the
	 * PropertyResourceBundle file.
	 * 
	 * @param key
	 *            | PropertyResourceBundle key
	 * @param arguments
	 *            | arguments that should fill the placeholder in the returned
	 *            PropertyResourceBundle value
	 * @return a localized string for the given PropertyResourceBundle key,
	 *         filled with the set arguments
	 */
	public static String translate(String key, Object... arguments) {
		return MessageFormat.format(Constants.bundle.getString(key), arguments);
	}

	/**
	 * Try to translate a key in the localized version defined in the
	 * PropertyResourceBundle file. This function is optimized for differing
	 * sentences depending on the amount of results.
	 * 
	 * @param key
	 *            | PropertyResourceBundle key
	 * @param arguments
	 *            | arguments that should fill the placeholder in the returned
	 *            PropertyResourceBundle value. The last argument gives the
	 *            count and decide which value will be returned.
	 * @return a localized string for the given amount depending
	 *         PropertyResourceBundle key, filled with the set arguments
	 */
	public static String translateN(String key, Object... arguments) {
		int last = (int) arguments[arguments.length - 1];
		String suffix = last == 0 ? "_0" : last == 1 ? "_1" : "_n";
		return translate(key + suffix, arguments);
	}

	/**
	 * Formats the x value of a point with respect to if Pi is set in the
	 * coordinate system.
	 * 
	 * @param cs
	 *            the coordinate system
	 * @param x
	 *            x-value
	 * @return formated string for the point
	 */
	public static String formatX(CoordinateSystem cs, double x) {
		String str = cs.xAxis.format(x);
		if (cs.pi && !"0".equals(str)) {
			str += " pi";
		}
		return str;
	}

	/**
	 * Formats the y value of a point.
	 * 
	 * @param cs
	 *            the coordinate system
	 * @param y
	 *            y-value
	 * @return formated string for the point
	 */
	public static String formatY(CoordinateSystem cs, double y) {
		return cs.yAxis.format(y);
	}

	/**
	 * Formats a Point that it is optimized for speech output. E.g. (x / y)
	 * 
	 * @param cs
	 *            the coordinate system
	 * @param point
	 *            The point that should be transformed into a textual
	 *            representation
	 * @param pi
	 *            whether to use a pi based axis
	 * @return formated string for the point with '/' as delimiter
	 */
	public static String formatForSpeech(CoordinateSystem cs, Point point) {
		return ((point.name != null && !point.name.isEmpty()) ? point.name + " " : "") + formatX(cs, point.x)
				+ " / " + formatY(cs, point.y);
	}

	/**
	 * Formats a Point that it is optimized for textual output and packed into
	 * the caption with brackets. E.g. E(x | y)
	 * 
	 * @param cs
	 *            the coordinate system
	 * @param point
	 *            The point that should be transformed into a textual
	 *            representation
	 * @param cap
	 *            The caption sting without brackets
	 * @return formated string for the point with '/' as delimiter if now
	 *         caption is set, otherwise packed in the caption with brackets and
	 *         the '|' as delimiter
	 */
	public static String formatForText(CoordinateSystem cs, Point point, String cap) {
		String p = formatX(cs, point.x) + " | " + formatY(cs, point.y);
		cap = cap.trim();
		return (cap != null && !cap.isEmpty()) ? cap + "(" + p + ")" : p;
	}
}
