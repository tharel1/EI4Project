package characterlistener;

import java.util.EventListener;

public interface IArmorListener extends EventListener
{
	public void onChange(CharacterIntValueEvent e);
	public void onGain(CharacterIntValueEvent e);
	public void onLoss(CharacterIntValueEvent e);
}
