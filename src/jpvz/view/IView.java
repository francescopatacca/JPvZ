package jpvz.view;

public interface IView {

    // ---------------------------------------------------------------
	// VISUAL METHODS
	// ---------------------------------------------------------------
    
    void showPanel(String panelId);
    void showError(String error);
    void repaintGame();
    void openMainGui();

	// ---------------------------------------------------------------
	// TIMER INITIALIZATION METHODS
	// ---------------------------------------------------------------

    void stopTimer();
    void startTimer();
    void pauseTimer();

}

