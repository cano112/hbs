package pl.edu.agh.hbs.simulation

import pl.edu.agh.hbs.core.model.Representation
import pl.edu.agh.hbs.core.state.WebClientConfigProvider

class WebClientConfigProviderImpl(val width: Int, val height: Int, val representations: Representation*) extends WebClientConfigProvider {

  override def getConfigString: String = "{\"width\":" + width + ", \"height\":" + height + ", \"config\":[" + representations.map(r => r.getConfig).mkString(",") + "]}"

  override def getWidth: Int = width

  override def getHeight: Int = height
}
