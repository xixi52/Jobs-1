package com.gamingmesh.jobs.commands.list;

import org.bukkit.command.CommandSender;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.commands.Cmd;
import com.gamingmesh.jobs.commands.JobCommand;
import com.gamingmesh.jobs.container.CurrencyType;
import com.gamingmesh.jobs.container.Job;

public class moneyboost implements Cmd {

    @Override
    @JobCommand(2400)
    public boolean perform(Jobs plugin, final CommandSender sender, final String[] args) {

	if (args.length > 2 || args.length <= 1) {
	    Jobs.getCommandManager().sendUsage(sender, "moneyboost");
	    return true;
	}

	double rate = 1.0;
	if (!args[1].equalsIgnoreCase("all") && !args[0].equalsIgnoreCase("reset"))
	    try {
		rate = Double.parseDouble(args[1]);
	    } catch (NumberFormatException e) {
		Jobs.getCommandManager().sendUsage(sender, "moneyboost");
		return true;
	    }

	Job job = Jobs.getJob(args[0]);
	if (job == null) {
	    sender.sendMessage(Jobs.getLanguage().getMessage("general.error.job"));
	    return true;
	}


	if (args[0].equalsIgnoreCase("reset") && args[1].equalsIgnoreCase("all")) {
	    for (Job one : Jobs.getJobs()) {
		one.addBoost(CurrencyType.MONEY, 1.0);
	    }

	    sender.sendMessage(Jobs.getLanguage().getMessage("command.moneyboost.output.allreset"));
	    return true;
	} else if (args[0].equalsIgnoreCase("reset")) {
	    boolean found = false;
	    for (Job one : Jobs.getJobs()) {
		if (one.getName().equalsIgnoreCase(args[1])) {
		    one.addBoost(CurrencyType.MONEY, 1.0);
		    found = true;
		    break;
		}
	    }

	    if (found) {
		sender.sendMessage(Jobs.getLanguage().getMessage("command.moneyboost.output.jobsboostreset", "%jobname%", job.getName()));
		return true;
	    }
	}

	if (args[0].equalsIgnoreCase("all")) {
	    for (Job one : Jobs.getJobs()) {
		one.addBoost(CurrencyType.MONEY, rate);
	    }

	    sender.sendMessage(Jobs.getLanguage().getMessage("command.moneyboost.output.boostalladded", "%boost%", rate));
	    return true;
	}

	job.addBoost(CurrencyType.MONEY, rate);
	sender.sendMessage(Jobs.getLanguage().getMessage("command.moneyboost.output.boostadded", "%boost%", rate, "%jobname%", job.getName()));
	return true;
    }
}
