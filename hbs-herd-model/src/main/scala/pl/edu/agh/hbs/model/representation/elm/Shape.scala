package pl.edu.agh.hbs.model.representation.elm

import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import pl.edu.agh.hbs.api.ui.Representation

class Shape(val name: String, private val parts: Part*) extends Representation {

  @transient private implicit val formats: DefaultFormats.type = net.liftweb.json.DefaultFormats

  override def getConfig: String = "{\"name\":\"" + name + "\",\"parts\":[" + parts.map(p => write(p)).mkString(",") + "]}"

  override def getIdentity: String = name

}
