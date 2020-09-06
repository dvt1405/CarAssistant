package kun.kt.vtv;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VtvFetchLinkStream extends AsyncTask<Void, Stream, ArrayList<Stream>> {

    private final static int VTV9_ID = 39;
    private final static int VTV9_CHANNEL = 9;
    private final static int VTV8_ID = 36;
    private final static int VTV8_CHANNEL = 8;
    private final static int VTV7_ID = 27;
    private final static int VTV7_CHANNEL = 7;
    private final static int VTV6_ID = 6;
    private final static int VTV6_CHANNEL = 6;
    private final static int VTV5_ID = 5;
    private final static int VTV5_CHANNEL = 5;
    private final static int VTV4_ID = 4;
    private final static int VTV4_CHANNEL = 4;
    private final static int VTV3_ID = 3;
    private final static int VTV3_CHANNEL = 3;
    private final static int VTV2_ID = 2;
    private final static int VTV2_CHANNEL = 2;
    private final static int VTV1_ID = 1;
    private final static int VTV1_CHANNEL = 1;

    private final static int VTV5_TNB_ID = 7;
    private final static int VTV5_TNB_CHANNEL = 5;

    private static Map<Integer, Integer> vtvChannelId = new HashMap<>();

    static {
        for (int i = 1; i <= 6; i++) {
            vtvChannelId.put(i, i);
        }
        vtvChannelId.put(VTV9_CHANNEL, VTV9_ID);
        vtvChannelId.put(VTV8_CHANNEL, VTV8_ID);
        vtvChannelId.put(VTV7_CHANNEL, VTV7_ID);
    }

    private int channel;
    private OnSuccessListener onSuccessListener;

    public VtvFetchLinkStream(int channel, OnSuccessListener onSuccessListener) {
        this.channel = channel;
        this.onSuccessListener = onSuccessListener;
    }

    @Override
    protected ArrayList<Stream> doInBackground(Void... voids) {
        if (!vtvChannelId.containsKey(this.channel)) {
            return null;
        }
        return new VTVGo().getLink(vtvChannelId.get(this.channel) + "");
    }

    @Override
    protected void onPostExecute(ArrayList<Stream> streams) {
        super.onPostExecute(streams);
        if (streams.size() > 0) {
            onSuccessListener.onGetSuccess(streams.get(streams.size() - 1));
        } else {

        }
    }

    public interface OnSuccessListener {
        void onGetSuccess(Stream stream);

        void onGetError();
    }
}
