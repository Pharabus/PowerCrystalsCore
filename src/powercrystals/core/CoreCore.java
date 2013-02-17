package powercrystals.core;

import java.io.File;
import java.util.Arrays;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import powercrystals.core.updater.IUpdateableMod;
import powercrystals.core.updater.UpdateManager;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class CoreCore extends DummyModContainer implements IUpdateableMod
{
	public static final String version = "1.4.6R1.0.2B2";
	public static final String modId = "PowerCrystalsCore";
	public static final String modName = "PowerCrystals Core";
	
	public static Property doUpdateCheck;

	public CoreCore()
	{
		super(new ModMetadata());
		ModMetadata md = super.getMetadata();
		md.modId = modId;
		md.name = modName;
		md.version = version.substring(version.indexOf('R') + 1);
		md.authorList = Arrays.asList("PowerCrystals");
		md.url = "http://www.minecraftforum.net/topic/1629898-";
		md.description = "Core functionality for Power Crystals' mods.";
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);
		return true;
	}
	
	@Subscribe
	public void preInit(FMLPreInitializationEvent evt)
	{
		loadConfig(evt.getSuggestedConfigurationFile());
	}
	
	@Subscribe
	public void load(FMLInitializationEvent evt)
	{
		TickRegistry.registerScheduledTickHandler(new UpdateManager(this), Side.CLIENT);
	}
	
	private void loadConfig(File f)
	{
		Configuration c = new Configuration(f);
		c.load();
		
		doUpdateCheck = c.get(Configuration.CATEGORY_GENERAL, "EnableUpdateCheck", true);
		doUpdateCheck.comment = "Set to false to disable update checks for all Power Crystals' mods.";
		
		c.save();
	}

	@Override
	public String getModId()
	{
		return modId;
	}

	@Override
	public String getModName()
	{
		return modName;
	}

	@Override
	public String getModFolder()
	{
		return modId;
	}

	@Override
	public String getModVersion()
	{
		return version;
	}
}