/*
* Code from annas project : Graph implementation and algorithm package
* https://code.google.com/p/annas/source/browse/src/annas/misc/Graph6.java
*/

public class Graph6{

    public static String encodeGraph(int NoNodes, String adjmatrix) {
        String rv = "";
        int[] nn = encodeN(NoNodes);
        int[] adj = encodeR(adjmatrix);
        int[] res = new int[nn.length + adj.length];
        System.arraycopy(nn, 0, res, 0, nn.length);
        System.arraycopy(adj, 0, res, nn.length, adj.length);
        for (int i = 0; i < res.length; i++) {
            rv = rv + (char) res[i];
        }
        return rv;
    }

    private static int[] encodeN(long i) {

        if (0 <= i && i <= 62) {
            return new int[] { (int) (i + 63) };
        } else if (63 <= i && i <= 258047) {
            int[] ret = new int[4];
            ret[0] = 126;
            int[] g = R(padL(Long.toBinaryString(i), 18));
            for (int j = 0; j < 3; j++)
                ret[j + 1] = g[j];
            return ret;
        } else {
            int[] ret = new int[8];
            ret[0] = 126;
            ret[1] = 126;
            int[] g = R(padL(Long.toBinaryString(i), 36));
            for (int j = 0; j < 6; j++)
                ret[j + 2] = g[j];
            return ret;
        }

    }

    private static int[] R(String a) {
        int[] bytes = new int[a.length() / 6];
        for (int i = 0; i < a.length() / 6; i++) {
            bytes[i] = Integer.valueOf(a.substring(i * 6, ((i * 6) + 6)), 2);
            bytes[i] = (byte) (bytes[i] + 63);
        }

        return bytes;
    }

    private static int[] encodeR(String a) {
        a = padR(a);
        return R(a);
    }

    private static String padR(String str) {
        int padwith = 6 - (str.length() % 6);
        for (int i = 0; i < padwith; i++) {
            str += "0";
        }
        return str;
    }

    private static String padL(String str, int h) {
        String retval = "";
        for (int i = 0; i < h - str.length(); i++) {
            retval += "0";
        }
        return retval + str;
    }
}