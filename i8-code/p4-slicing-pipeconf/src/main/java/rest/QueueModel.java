package rest;


public class QueueModel {
    private long id;
    private long T_DELAY;
    private long M_DELAY;
    private long C_DELAY;

    public QueueModel(long id, long t_delay,long m_delay,long c_delay) {
        this.id = id;
        this.T_DELAY = t_delay;
        this.M_DELAY = m_delay;
        this.C_DELAY = c_delay;
    }

    public long getC_DELAY() {
        return C_DELAY;
    }

    public void setC_DELAY(long c_DELAY) {
        C_DELAY = c_DELAY;
    }

    public long getM_DELAY() {
        return M_DELAY;
    }

    public void setM_DELAY(long m_DELAY) {
        M_DELAY = m_DELAY;
    }

    public long getT_DELAY() {
        return T_DELAY;
    }

    public void setT_DELAY(long t_DELAY) {
        T_DELAY = t_DELAY;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
