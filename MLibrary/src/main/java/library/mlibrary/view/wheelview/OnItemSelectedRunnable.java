package library.mlibrary.view.wheelview;

public class OnItemSelectedRunnable implements Runnable {
   public LoopView loopView;

    public OnItemSelectedRunnable(LoopView loopview) {
        loopView = loopview;
    }

    @Override
    public void run() {
        loopView.onItemSelectedListener.onItemSelected(loopView.getSelectedItem());
    }
}
