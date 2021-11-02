package learn.chess.model;

public class Ratio {

    private int ratioId;
    private int winRatio;
    private int lossRatio;
    private int tieRatio;

    public int getRatioId() {
        return ratioId;
    }

    public void setRatioId(int ratioId) {
        this.ratioId = ratioId;
    }

    public int getWinRatio() {
        return winRatio;
    }

    public void setWinRatio(int winRatio) {
        this.winRatio = winRatio;
    }

    public int getLossRatio() {
        return lossRatio;
    }

    public void setLossRatio(int lossRatio) {
        this.lossRatio = lossRatio;
    }

    public int getTieRatio() {
        return tieRatio;
    }

    public void setTieRatio(int tieRatio) {
        this.tieRatio = tieRatio;
    }
}
