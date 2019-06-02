package cj.netos.boudbank.program.reactor;

import java.util.Map;

import cj.studio.ecm.EcmException;
import cj.studio.util.reactor.Event;
import cj.studio.util.reactor.IPipeline;
import cj.studio.util.reactor.IValve;
//调度并实现扣费
public class MyReactorControllerVavle implements IValve {
	Map<String, IValve> valves;
	public MyReactorControllerVavle(Map<String, IValve> valves) {
		this.valves=valves;
	}

	@Override
	public void flow(Event e, IPipeline pipeline) {
		IValve valve=valves.get(e.getCmd());
		if(valve==null) {
			throw new EcmException("不存在valve:"+e.getCmd());
		}
		valve.flow(e, pipeline);
	}

}
