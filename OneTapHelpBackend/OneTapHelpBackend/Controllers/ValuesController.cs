using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using System.Text;
using Newtonsoft.Json;
namespace OneTapHelpBackend.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ValuesController : ControllerBase
    {
        public static Database database;
        public static int timer;
        // GET api/values
        [HttpGet]
        public ActionResult<string> Get()
        {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0;i < database.list.Count;i++)
            {
                stringBuilder.Append(Entity.GetDataLine(database.list[i]));
                if (i != database.list.Count-1)
                    stringBuilder.Append("@");
            }
            return stringBuilder.ToString();
        }

        [HttpGet("{param}")]
        public ActionResult<string> Get(String param)
        {
            if (param == "refresh")
            {
                try
                {
                    String result = MessageRetrive.getMessages();
                    return result;
                    if (result != null)
                    {
                        Packet packet = (Packet)JsonConvert.DeserializeObject(result, typeof(Packet));
                        if (packet != null)
                            if (packet.messages != null)
                            {
                                for (int i = 0; i < packet.messages.Count; i++)
                                {
                                    String s = packet.messages[i].message.Substring(5);
                                    Entity entity = Entity.ParseEntity(packet.messages[i].number + "_" + s + "_123");
                                    if (entity != null)
                                    {
                                        int index;
                                        if ((index = database.GetIndex(packet.messages[i].number)) != -1)
                                        {
                                            database.DeleteFromList(index);
                                            database.AddToList(entity);
                                        }
                                        else
                                        {
                                            database.AddToList(entity);
                                        }
                                    }
                                    else
                                    {
                                    }
                                }
                            }
                            else
                            {
                            }
                    }
                }
                catch
                {
                }
                Database.WriteToFile(database);
                return "DONE";
            }
            else if (param == "delete")
            {
                database.list.Clear();
                Database.WriteToFile(database);
                return "DELETED";
            }
            else
            {
                Entity entity = Entity.ParseEntity(param);
                if (entity != null)
                {
                    database.AddToList(entity);
                    Database.WriteToFile(database);
                    return Entity.GetDataLine(entity);
                }
                else
                {
                    return "PARSE_ERROR";
                }
                
            }
        }
        // POST api/values
        [HttpPost]
        public void Post([FromBody] string value)
        {
            
        }

        // PUT api/values/5
        [HttpPut("{id}")]
        public void Put(int id, [FromBody] string value)
        {
        }

        // DELETE api/values/5
        [HttpDelete("{id}")]
        public void Delete(int id)
        {
        }
    }
}
