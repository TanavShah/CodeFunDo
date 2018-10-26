using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.IO;

namespace OneTapHelpBackend
{
    public class Database
    {
        public const String filename = "database.txt";
        public List<Entity> list = new List<Entity>();
        public bool dirty;

        public void AddToList(Entity entity)
        {
            if (entity != null)
            {
                list.Add(entity);
                dirty = true;
            }
        }
        public void DeleteFromList(int index)
        {
            if (index < list.Count && index >= 0)
            {
                list.RemoveAt(index);
                dirty = true;
            }
        }
        public int GetIndex(long phoneNumber)
        {
            for (int i = 0;i < list.Count;i++)
            {
                if (list[i].phoneNumber == phoneNumber)
                {
                    return i;
                }
            }
            return -1;
        }


        public static Database LoadFromFile()
        {
            Database database = new Database();
            string filePath = Path.Combine(Environment.GetFolderPath(System.Environment.SpecialFolder.CommonApplicationData), filename);
            try
            {
                FileStream stream = new FileStream(filePath, FileMode.Open);
                try
                {
                    using (StreamReader reader = new StreamReader(stream))
                    {
                        String line;
                        while ((line = reader.ReadLine()) != null)
                        {
                            Entity entity = Entity.ParseEntity(line);
                            database.AddToList(entity);
                        }
                    }
                }
                catch
                {

                }
                stream.Close();
                try
                {
                    stream.Dispose();
                }
                catch
                {

                }
            }
            catch
            {

            }
            return database;
        }
        public static void WriteToFile(Database database)
        {
            string filePath = Path.Combine(Environment.GetFolderPath(System.Environment.SpecialFolder.CommonApplicationData), filename);
            try
            {
                FileStream stream = new FileStream(filePath, FileMode.Create);
                try
                {
                    using (StreamWriter writer = new StreamWriter(stream))
                    {
                        for (int i = 0;i < database.list.Count;i++)
                        {
                            writer.WriteLine(Entity.GetDataLine(database.list[i]));
                        }
                        writer.Flush();
                    }
                }
                catch
                {

                }
                stream.Close();
                try
                {
                    stream.Dispose();
                }
                catch
                {

                }
            }
            catch
            {

            }
        }
    }
}
