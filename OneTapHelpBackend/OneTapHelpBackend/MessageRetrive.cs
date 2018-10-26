using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Net;
using System.Collections.Specialized;
using System.IO;
using System.Text;
using Newtonsoft.Json;

namespace OneTapHelpBackend
{
    public class MessageRetrive
    {
        static String apiKey = "XO5nRoufMrE-zGb1kWlXQHEJLZ3opIUYjEC1M2VM9m";
        static String inboxId = "10";
        static void Retrive()
        {

        }
        public static string getMessages()
        {
            String result;

            String url = "https://api.textlocal.in/get_messages/?apikey=" + apiKey + "&inbox_id=" + inboxId;
            //refer to parameters to complete correct url string

            StreamWriter myWriter = null;
            HttpWebRequest objRequest = (HttpWebRequest)WebRequest.Create(url);

            objRequest.Method = "POST";
            objRequest.ContentLength = Encoding.UTF8.GetByteCount(url);
            objRequest.ContentType = "application/x-www-form-urlencoded";
            try
            {
                myWriter = new StreamWriter(objRequest.GetRequestStream());
                myWriter.Write(url);
            }
            catch (Exception e)
            {
                return e.Message;
            }
            finally
            {
                myWriter.Close();
            }

            HttpWebResponse objResponse = (HttpWebResponse)objRequest.GetResponse();
            using (StreamReader sr = new StreamReader(objResponse.GetResponseStream()))
            {
                result = sr.ReadToEnd();
                // Close and clean up the StreamReader
                sr.Close();
            }
            return result;
        }
    }
}
class MessagePacket
{
    [JsonProperty("number")]
    public long number;
    [JsonProperty("message")]
    public String message;
    [JsonProperty("isNew")]
    public string isNew;
}
class Packet
{
    [JsonProperty("messages")]
    public List<MessagePacket> messages;
}