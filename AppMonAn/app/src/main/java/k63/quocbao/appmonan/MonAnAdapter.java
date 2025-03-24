package k63.quocbao.appmonan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MonAnAdapter extends BaseAdapter {
    //
    private ArrayList<MonAn> dsMonAn;
    private LayoutInflater layoutInflater;
    private Context context;
    //ham tao

    public MonAnAdapter(Context _context, ArrayList<MonAn> dsMonAn) {
        this.dsMonAn = dsMonAn;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return dsMonAn.size();
    }

    @Override
    public Object getItem(int i) {
        return dsMonAn.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // View item hien hanh
        View viewHienHanh = view;
        //Kiem tra
        if(viewHienHanh == null)
           viewHienHanh = layoutInflater.inflate(R.layout.item_monan, null);
        // Lay du lieu
        MonAn monAnHienTai = dsMonAn.get(i);
        // gan len cac dieu khien

        //Tim dieu Khien
        TextView textView_TenMon =(TextView) viewHienHanh.findViewById(R.id.tvTenMonAn);
        TextView textView_DonGia =(TextView) viewHienHanh.findViewById(R.id.tvDonGia);
        TextView textView_MoTa =(TextView) viewHienHanh.findViewById(R.id.tvMoTa);
        ImageView imageView_Anh = (ImageView) viewHienHanh.findViewById(R.id.imAnhDaiDien);
        // Set len
        textView_TenMon.setText(monAnHienTai.getTenMonAn());
        textView_DonGia.setText(String.valueOf(monAnHienTai.getDonGia()));
        textView_MoTa.setText((monAnHienTai.getMoTa()));
        imageView_Anh.setImageResource(monAnHienTai.getIdAnhminhhoa());

        return viewHienHanh;

    }
}
