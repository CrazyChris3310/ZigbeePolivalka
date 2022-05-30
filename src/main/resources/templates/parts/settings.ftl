<#import "common.ftl" as com>


<@com.page>
    <div>${flower.getName()}</div>
    <form action = "/flowers/${flower.getId()}" method="post">
        <label for="name">
            Name
            <input id="name" value="${flower.getName()}" maxlength="255">
        </label>
        <div>
            Moisture
            ${flower.getCurrentMoistureLevel()}%
        </div>
        Watering Mode
        <script type="text/javascript">
            function check(){
                let mode1 = document.getElementById("mode1");
                let moisture = document.getElementById("moisture_mode");
                let time = document.getElementById("time_mode");
                console.log(mode1.checked)
                if (mode1.checked === true){
                    moisture.setAttribute("style", "visibility: visible");
                    time.setAttribute("style", "visibility: hidden");
                    time.setAttribute("style", "display: none");
                } else {
                    moisture.setAttribute("style", "visibility: hidden");
                    moisture.setAttribute("style", "display: none");
                    time.setAttribute("style", "visibility: visible");
                }
            }
        </script>
        <#if flower.getWateringMode().getModeId() == 1>
            <label for="moisture">
                Watering
                <input id="mode1" type="radio" name="moisture" value="1" checked onclick="check()">
                Time
                <input id="mode2" type="radio" name="moisture" value="2" onclick="check()">
            </label>
            <div id="moisture_mode">
                <label for="levels">
                    Choose moisture level
                    <select required name="levels">
                        <#list 0..9 as i>
                            <option>${i*10}</option>
                        </#list>
                    </select>
                </label>
            </div>
            <div id="time_mode" style="visibility : hidden; display: none">
                <label for="time">
                    <select required name="time">
                        <#list 0..30 as days>
                            <option>${days}</option>
                        </#list>
                    </select>
                    Days
                    <select required name="time">
                        <#list 0..24 as hours>
                            <option>${hours}</option>
                        </#list>
                    </select>
                    Hours
                    <select required name="time">
                        <#list 0..60 as min>
                            <option>${min}</option>
                        </#list>
                    </select>
                    Min
                </label>
            </div>
        <#else>
            <label for="moisture">
                Watering
                <input id="mode1" type="radio" name="moisture" value="1" onclick="check()">
                Time
                <input id="mode2" type="radio" name="moisture" value="2" checked onclick="check()">
            </label>
            <div id="moisture_mode" style="visibility : hidden; display: none">
                <label for="levels">
                    Choose moisture level
                    <select required name="levels">
                        <#list 0..9 as i>
                            <option>${i*10}</option>
                        </#list>
                    </select>
                </label>
            </div>
            <div id="time_mode">
                <label for="time">
                    <select required name="time">
                        <#list 0..30 as days>
                            <option>${days}</option>
                        </#list>
                    </select>
                    Days
                    <select required name="time">
                        <#list 0..24 as hours>
                            <option>${hours}</option>
                        </#list>
                    </select>
                    Hours
                    <select required name="time">
                        <#list 0..60 as min>
                            <option>${min}</option>
                        </#list>
                    </select>
                    Min
                </label>
            </div>
        </#if>
        <input type="submit" value="Submit">
        <input type="reset" value="Reset">
    </form>
    <a href="/flowers">Go to Main Page</a>
</@com.page>