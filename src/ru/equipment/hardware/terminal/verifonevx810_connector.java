package ru.equipment.hardware.terminal;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.Process;
import java.lang.ProcessBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.util.HashMap;

public class verifonevx810_connector
{
    private final String POS_DIR = "UPOS";
    private final String INCOME_SCRIPT_NAME = "income.sh";
    private final String OUTCOME_SCRIPT_NAME = "outcome.sh";
    private final String REVISE_SCRIPT_NAME = "revise.sh";
    private final String ANSWER_FILE_NAME = "e";
    private final String CHECK_FILE_NAME = "p";

    public HashMap<String, ArrayList> income(Double amount)
    {
        HashMap<String, ArrayList> result = null;
        int raw_amount = new Double(amount * 100).intValue();

        try
        {
            List<String> commands = new ArrayList<String>();
            commands.add("/bin/bash");
            commands.add("/home/" + System.getProperty("user.name") + "/" + this.POS_DIR + "/" + this.INCOME_SCRIPT_NAME);
            commands.add(Integer.toString(raw_amount));

            ProcessBuilder builder = new ProcessBuilder(commands);
            builder.directory(new File("/home/" + System.getProperty("user.name") + "/" + this.POS_DIR));
            builder.redirectErrorStream(true);

            Process process = builder.start();

            StringBuilder out = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null, previous = null;

            while ((line = br.readLine()) != null)
            {
                if (!line.equals(previous))
                {
                    previous = line;
                    out.append(line).append('\n');
                    System.out.println(line);
                }
            }

            if (process.waitFor() == 0)
            {
                result = new HashMap<String, ArrayList>();

                File answer_file = new File("/home/" + System.getProperty("user.name") + "/" + this.POS_DIR + "/" + this.ANSWER_FILE_NAME);
                if(answer_file.exists())
                {
                    BufferedReader answer_file_reader = new BufferedReader(new InputStreamReader(new FileInputStream(answer_file), "KOI8-R"));
                    ArrayList<String> answer_array = new ArrayList<String>();
                    String answer_line = null;

                    while ((answer_line = answer_file_reader.readLine()) != null)
                    {
                        answer_array.add(answer_line);
                    }

                    result.put("answer", answer_array);
                }

                File check_file = new File("/home/" + System.getProperty("user.name") + "/" + this.POS_DIR + "/" + this.CHECK_FILE_NAME);
                if (check_file.exists())
                {
                    BufferedReader check_file_reader = new BufferedReader(new InputStreamReader(new FileInputStream(check_file), "KOI8-R"));
                    ArrayList<String> check_array = new ArrayList<String>();
                    String check_line = null;

                    while ((check_line = check_file_reader.readLine()) != null)
                    {
                        check_array.add(check_line);
                    }

                    result.put("check", check_array);
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return result;
    }

    public HashMap<String, ArrayList> outcome(Double amount)
    {
        HashMap<String, ArrayList> result = null;
        int raw_amount = new Double(amount * 100).intValue();

        try
        {
            List<String> commands = new ArrayList<String>();
            commands.add("/bin/bash");
            commands.add("/home/" + System.getProperty("user.name") + "/" + this.POS_DIR + "/" + this.OUTCOME_SCRIPT_NAME);
            commands.add(Integer.toString(raw_amount));

            ProcessBuilder builder = new ProcessBuilder(commands);
            builder.directory(new File("/home/" + System.getProperty("user.name") + "/" + this.POS_DIR));
            builder.redirectErrorStream(true);

            Process process = builder.start();

            StringBuilder out = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null, previous = null;

            while ((line = br.readLine()) != null)
            {
                if (!line.equals(previous))
                {
                    previous = line;
                    out.append(line).append('\n');
                    System.out.println(line);
                }
            }

            if (process.waitFor() == 0)
            {
                result = new HashMap<String, ArrayList>();

                File answer_file = new File("/home/" + System.getProperty("user.name") + "/" + this.POS_DIR + "/" + this.ANSWER_FILE_NAME);
                if(answer_file.exists())
                {
                    BufferedReader answer_file_reader = new BufferedReader(new InputStreamReader(new FileInputStream(answer_file), "KOI8-R"));
                    ArrayList<String> answer_array = new ArrayList<String>();
                    String answer_line = null;

                    while ((answer_line = answer_file_reader.readLine()) != null)
                    {
                        answer_array.add(answer_line);
                    }

                    result.put("answer", answer_array);
                }

                File check_file = new File("/home/" + System.getProperty("user.name") + "/" + this.POS_DIR + "/" + this.CHECK_FILE_NAME);
                if (check_file.exists())
                {
                    BufferedReader check_file_reader = new BufferedReader(new InputStreamReader(new FileInputStream(check_file), "KOI8-R"));
                    ArrayList<String> check_array = new ArrayList<String>();
                    String check_line = null;

                    while ((check_line = check_file_reader.readLine()) != null)
                    {
                        check_array.add(check_line);
                    }

                    result.put("check", check_array);
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return result;
    }

    public HashMap<String, ArrayList> revise()
    {
        HashMap<String, ArrayList> result = null;

        try
        {
            List<String> commands = new ArrayList<String>();
            commands.add("/bin/bash");
            commands.add("/home/" + System.getProperty("user.name") + "/" + this.POS_DIR + "/" + this.REVISE_SCRIPT_NAME);

            ProcessBuilder builder = new ProcessBuilder(commands);
            builder.directory(new File("/home/" + System.getProperty("user.name") + "/" + this.POS_DIR));
            builder.redirectErrorStream(true);

            Process process = builder.start();

            StringBuilder out = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null, previous = null;

            while ((line = br.readLine()) != null)
            {
                if (!line.equals(previous))
                {
                    previous = line;
                    out.append(line).append('\n');
                    System.out.println(line);
                }
            }

            if (process.waitFor() == 0)
            {
                result = new HashMap<String, ArrayList>();

                File answer_file = new File("/home/" + System.getProperty("user.name") + "/" + this.POS_DIR + "/" + this.ANSWER_FILE_NAME);
                if(answer_file.exists())
                {
                    BufferedReader answer_file_reader = new BufferedReader(new InputStreamReader(new FileInputStream(answer_file), "KOI8-R"));
                    ArrayList<String> answer_array = new ArrayList<String>();
                    String answer_line = null;

                    while ((answer_line = answer_file_reader.readLine()) != null)
                    {
                        answer_array.add(answer_line);
                    }

                    result.put("answer", answer_array);
                }

                File check_file = new File("/home/" + System.getProperty("user.name") + "/" + this.POS_DIR + "/" + this.CHECK_FILE_NAME);
                if (check_file.exists())
                {
                    BufferedReader check_file_reader = new BufferedReader(new InputStreamReader(new FileInputStream(check_file), "KOI8-R"));
                    ArrayList<String> check_array = new ArrayList<String>();
                    String check_line = null;

                    while ((check_line = check_file_reader.readLine()) != null)
                    {
                        check_array.add(check_line);
                    }

                    result.put("check", check_array);
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return result;
    }
}
