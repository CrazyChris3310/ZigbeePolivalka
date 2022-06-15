<#import "common.ftl" as com>

<@com.page>
    <h2>${flower.getName()}</h2>
    <form action = "/${flower.getId()}" method="post">
        <label for="name">
            Name
            <input placeholder="Введите имя" id="name" name="name" value="${flower.getName()}" maxlength="255" required>
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
            <label for="watering_mode">
                Moisture
                <input id="mode1" class="form-radio-button" type="radio" name="watering_mode" value="1" checked onclick="check()">
                Time
                <input id="mode2" class="form-radio-button" type="radio" name="watering_mode" value="2" onclick="check()">
            </label>
            <div id="moisture_mode">
                <label for="levels">
                    Choose moisture level
                    <select class="form-select-field" required id="level" name="levels">
                        <#list 0..9 as i>
                            <#if flower.getWateringMode().getModeParameter() == i*10>
                                <option selected>${i*10}</option>
                            <#else>
                                <option>${i*10}</option>
                            </#if>
                        </#list>
                    </select>
                </label>
            </div>
            <div id="time_mode" style="visibility : hidden; display: none">
                <label for="days">
                    <select class="form-select-field" required id="days" name="days">
                        <#list 0..30 as days>
                            <option>${days}</option>
                        </#list>
                    </select>
                    Days
                </label>
                <label for="hours">
                    <select class="form-select-field" required id="hours" name="hours">
                        <#list 0..24 as hours>
                            <option>${hours}</option>
                        </#list>
                    </select>
                    Hours
                </label>
                <label for="min">
                    <select class="form-select-field" required id="min" name="min">
                        <#list 0..60 as min>
                            <option>${min}</option>
                        </#list>
                    </select>
                    Min
                </label>
            </div>
        <#else>
            <label for="watering_mode">
                Moisture
                <input id="mode1" class="form-radio-button" type="radio" name="watering_mode" value="1" onclick="check()">
                Time
                <input id="mode2" class="form-radio-button" type="radio" name="watering_mode" value="2" checked onclick="check()">
            </label>
            <div id="moisture_mode" style="visibility : hidden; display: none">
                <label for="levels">
                    Choose moisture level
                    <select class="form-select-field" required id="level" name="levels">
                        <#list 0..9 as i>
                            <option>${i*10}</option>
                        </#list>
                    </select>
                </label>
            </div>
            <div id="time_mode">
                <label for="time">
                    <select class="form-select-field" required id="days" name="days">
                        <#list 0..30 as days>
                            <#if flower.getWateringMode().getModeParameter() / 60 / 24 == days>
                                <option selected>${days}</option>
                            <#else>
                                <option>${days}</option>
                            </#if>
                        </#list>
                    </select>
                    Days
                    <select class="form-select-field" required id="hours" name="hours">
                        <#list 0..24 as hours>
                            <#if flower.getWateringMode().getModeParameter() / 60 % 24 == hours>
                                <option selected>${hours}</option>
                            <#else>
                                <option>${hours}</option>
                            </#if>
                        </#list>
                    </select>
                    Hours
                    <select class="form-select-field" required id="min" name="min">
                        <#list 0..60 as min>
                            <#if flower.getWateringMode().getModeParameter() % 60 == min>
                                <option selected>${min}</option>
                            <#else>
                                <option>${min}</option>
                            </#if>
                        </#list>
                    </select>
                    Min
                </label>
            </div>
        </#if>
        <div>
            <label for="valve-open-time">Valve open time:
                <select required id="valve-open-time" name="valve_open_time">
                    <#list 5..30 as val>
                        <#if (val)%5 == 0>
                            <option value="${val}">${val}</option>
                        </#if>
                    </#list>
                </select>
            </label>
        </div>
        <div class="form-buttons-container">
            <input class="form-submit-reset-button" type="submit" value="Submit">
            <input class="form-submit-reset-button" type="reset" value="Reset">
        </div>
    </form>
    <a href="/flowers">BACK TO MAIN PAGE</a>
</@com.page>