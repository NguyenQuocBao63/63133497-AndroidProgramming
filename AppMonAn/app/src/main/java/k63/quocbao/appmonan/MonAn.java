package k63.quocbao.appmonan;

public class MonAn {
    private String tenMonAn;
    private double DonGia;
    private String MoTa;
    private int idAnhminhhoa;
    //ham tao

    public MonAn(String tenMonAn, double donGia,String moTa, int idAnhminhhoa) {
        this.tenMonAn = tenMonAn;
        this.DonGia = donGia;
        this.MoTa = moTa;
        this.idAnhminhhoa = idAnhminhhoa;
    }
    // cac getter va setter
    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public double getDonGia() {
        return DonGia;
    }

    public void setDonGia(double donGia) {
        DonGia = donGia;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public int getIdAnhminhhoa() {
        return idAnhminhhoa;
    }

    public void setIdAnhminhhoa(int idAnhminhhoa) {
        this.idAnhminhhoa = idAnhminhhoa;
    }
}

