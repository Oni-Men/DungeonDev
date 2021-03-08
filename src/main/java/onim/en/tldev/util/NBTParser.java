package onim.en.tldev.util;

import java.util.Collections;
import java.util.List;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class NBTParser {

	public static String parseNBT(NBTTagCompound tagCompound) {
		return parseNBT(tagCompound, 0);
	}

	private static String parseNBT(NBTTagCompound tagCompound, int tabIndex) {
		String text = "";

		if (tagCompound == null)
			return text;

		for (String key : tagCompound.getKeySet()) {

			NBTBase tag = tagCompound.getTag(key);

			String indent = String.join("", Collections.nCopies(tabIndex, "  "));

			if (tag instanceof NBTTagCompound) {
				text += indent + key + ":\n" + parseNBT((NBTTagCompound) tag, tabIndex + 1);
			} else {
				text += indent + key + ": " + tag.toString() + "\n";
			}

		}

		return text;
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> convertNBTTagList2List(NBTTagList src, List<T> dest) {
		dest.clear();
		for (int i = 0; i < src.tagCount(); i++) {
			dest.add((T) src.get(i));
		}
		return dest;
	}

}
