package com.jack.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.script.ScriptJob;
import com.jack.elasticjob.annotation.ElasticJobConf;

/**
 * 脚本任务不需要写逻辑，逻辑在被执行的脚本中，这边只是定义一个任务而已
 */
@ElasticJobConf(name = "MyScriptJob")
public class MyScriptJob implements ScriptJob {

	public void execute(ShardingContext context) {
		
	}

}