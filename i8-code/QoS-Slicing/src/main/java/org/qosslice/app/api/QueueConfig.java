package org.qosslice.app.api;

import com.google.common.base.MoreObjects;

public class QueueConfig {

  private long M_DELAY;
  private long T_DELAY;
  private long C_DELAY;

  public QueueConfig(long m_DELAY, long t_DELAY, long c_DELAY) {
    M_DELAY = m_DELAY;
    T_DELAY = t_DELAY;
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

  public long getC_DELAY() {
    return C_DELAY;
  }

  public void setC_DELAY(long c_DELAY) {
    C_DELAY = c_DELAY;
  }

  @Override
  public String toString() {
    return "QueueConfig{" +
            "M_DELAY=" + getM_DELAY() +
            ", T_DELAY='" + getT_DELAY() +
            ", C_DELAY='" + getC_DELAY() +
            '\'' +
            '}';
  }

  public QueueConfig copy() {
    return new QueueConfig(this.getM_DELAY(), this.getT_DELAY(), this.getC_DELAY());
  }
}