package jpvz.view;


public class View implements IView{

	//---------------------------------------------------------------
	// STATIC FIELDS
	//---------------------------------------------------------------
    
    private static View instance;

	//---------------------------------------------------------------
	// INSTANCE ATTRIBUTES
	//---------------------------------------------------------------

    private MainGUI mainGui=null;

    private View(){
        
    }

	//---------------------------------------------------------------
	// INSTANCE METHODS
	//---------------------------------------------------------------

    public void openMainGui(){
        javax.swing.SwingUtilities.invokeLater(() -> {
            this.mainGui = new MainGUI();
            this.mainGui.setVisible(true);
            this.mainGui.setLocationRelativeTo(null);
        });
    }

    public void showPanel(String panelId) {
        if (this.mainGui != null) {
            this.mainGui.showPanel(panelId);
        }
    }

    public void showError(String error) {
        if (this.mainGui != null) {
            this.mainGui.showError(error);
        }
    } 

    public void repaintGame(){
        if(this.mainGui!=null){
            this.mainGui.repaintGame();
        }
    }

	// ---------------------------------------------------------------
	// TIMER INITIALIZATION METHODS
	// ---------------------------------------------------------------

    public void startTimer(){
        if (this.mainGui != null) {
            this.mainGui.startTimer();
        }
    }

    public void stopTimer(){
        if (this.mainGui != null) {
            this.mainGui.stopTimer();
        }
    }

    public void pauseTimer(){
        if (this.mainGui != null) {
            this.mainGui.pauseTimer();
        }
    }

	//---------------------------------------------------------------
	// STATIC METHODS
	//---------------------------------------------------------------

    public static IView getInstance(){
        if(instance==null){
            instance=new View();
        }
        return instance;
    }

}
