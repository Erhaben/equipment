Array.prototype.in_array = function(p_val) {
	for(var i = 0, l = this.length; i < l; i++)	{
		if(this[i] == p_val) {
			return true;
		}
	}
	return false;
}


var plugin = {
	printer: {
		mode: 99, 
		submode: 99,

		session: {
			open: function(){
				if (plugin.printer.mode == 4)
				{
					var answer = equipment_applet.printerOpenSession();

					if (answer != -1){
						var object = JSON.parse(answer);
					if(object.error_code == 0)
						console.log("[Applet] Выполнено");
					else
						console.warn("[Applet] Не удалось выполнить открытие смены ( программная ошибка )");
					}
					else
						console.warn("[Applet] Не удалось выполнить открытие смены ( аппаратная ошибка )");
				}					
				else
					console.warn("[Applet] Не могу открыть смену т.к. текущая смена не закрыта. Текущий статус - " + plugin.printer.modes[plugin.printer.mode]);
			}, 
			close: function(){
				if ([2,3].in_array(plugin.printer.mode))
				{
					var answer = equipment_applet.printerCloseSession();

					if (answer != -1){
						var object = JSON.parse(answer);
					if(object.error_code == 0)
						console.log("[Applet] Выполнено");
					else
						console.warn("[Applet] Не удалось выполнить закрытие смены ( программная ошибка )");
					}
					else
						console.warn("[Applet] Не удалось выполнить закрытие смены ( аппаратная ошибка )");
				}					
				else
					console.warn("[Applet] Не могу закрыть смену. Текущий статус - " + plugin.printer.modes[plugin.printer.mode]);
			}
		},
		cut: function(){
			if (this.mode >= 0 && this.mode != 99)
			{
				var answer  = equipment_applet.printerCut();

				if (answer != -1){
					var object = JSON.parse(answer);
					if(object.error_code == 0)
						console.log("[Applet] Выполнено");
					else
                        console.warn("[Applet] Не удалось выполнить операцию, ошибка " + object.error_code + ' (' + plugin.printer.errors[object.error_code] + ')');
				}
				else
					console.warn("[Applet] Не удалось выполнить отрезку бумаги ( аппаратная ошибка )");
			}				
			else
				console.warn("[Applet] Апплет не готов");
		}, 
		beep: function(){
			if (this.mode >= 0 && this.mode != 99)
			{
				var answer = equipment_applet.printerBeep();	

				if (answer != -1){
					var object = JSON.parse(answer);
					if(object.error_code == 0)
						console.log("[Applet] Выполнено");
					else
						console.warn("[Applet] Не удалось выполнить звуковой сигнал ( программная ошибка )");
				}
				else
					console.warn("[Applet] Не удалось выполнить звуковой сигнал ( аппаратная ошибка )");
			}				
			else
				console.warn("[Applet] Апплет не готов");				
		}, 
		broach: function(lines){
			if (this.mode >= 0 && this.mode != 99)
			{
				var answer = equipment_applet.printerBroach(lines);	

				if (answer != -1){
					var object = JSON.parse(answer);
					if(object.error_code == 0)
						console.log("[Applet] Выполнено");
					else
						console.warn("[Applet] Не удалось выполнить прокрутку ленты ( программная ошибка )");
				}
				else
					console.warn("[Applet] Не удалось выполнить прокрутку ленты ( аппаратная ошибка )");	
			}				
			else
				console.warn("[Applet] Апплет не готов");
		}, 
		income: function(amount){
			if ([1,2,3].in_array(this.mode))
			{
				var answer = equipment_applet.printerIncome(amount);

				if (answer != -1){
					var object = JSON.parse(answer);
					if(object.error_code == 0)
						console.log("[Applet] Выполнено");
					else
                        console.warn("[Applet] Не удалось выполнить операцию, ошибка " + object.error_code + ' (' + plugin.printer.errors[object.error_code] + ')');
				}
				else
					console.warn("[Applet] Не удалось выполнить внесение денег ( аппаратная ошибка )");
			}				
			else
				console.warn("[Applet] Апплет не готов");
		}, 
		outcome: function(){
			if ([1,2,3].in_array(this.mode))
			{
				var answer = equipment_applet.printerOutcome(amount);

				if (answer != -1){
					var object = JSON.parse(answer);
					if(object.error_code == 0)
						console.log("[Applet] Выполнено");
					else
                        console.warn("[Applet] Не удалось выполнить операцию, ошибка " + object.error_code + ' (' + plugin.printer.errors[object.error_code] + ')');
				}
				else
					console.warn("[Applet] Не удалось выполнить выдачу денег ( аппаратная ошибка )");
			}				
			else
				console.warn("[Applet] Апплет не готов");
		},
        continue: function(){
            var a = -1;

            if([88].in_array(plugin.printer.mode))
            {
                var answer  = equipment_applet.printerContinuePrint();

                if (answer != -1){
                    var object = JSON.parse(answer);
                    if(object.error_code == 0)
                        console.log("[Applet] Выполнено");
                    else
                        console.warn("[Applet] Не удалось выполнить продолжение печати ( программная ошибка )");
                }
                else
                    console.warn("[Applet] Не удалось выполнить продолжение печати ( аппаратная ошибка )");
            }
            else
                console.warn('[Applet] Апплет не готов');
        },
		check: {
			init: function(){
				if ([1,2,3].in_array(plugin.printer.mode))
					equipment_applet.printerInitCheck();
				else
					console.warn("[Applet] Апплет не готов");
			}, 
			setType: function(type){
				if ([1,2,3].in_array(plugin.printer.mode))
					equipment_applet.printerSetCheckType(type);
				else
					console.warn("[Applet] Апплет не готов");
			}, 
			addProduct: function(title, count, price){
				if ([1,2,3].in_array(plugin.printer.mode))
					equipment_applet.printerAddProductToCheck(title, count, price);
				else
					console.warn("[Applet] Апплет не готов");
			}, 
			addPayment: function(index, amount){
				if ([1,2,3].in_array(plugin.printer.mode))
					equipment_applet.printerAddPaymentToCheck(index, amount);
				else
					console.warn("[Applet] Апплет не готов");
			}, 
			print: function(){
                var a = -1;
				if ([1,2,3].in_array(plugin.printer.mode))
				{
					var answer = equipment_applet.printerPrintCheck();

					if (answer != -1){
                        var object = JSON.parse(answer);
                        if(object.error_code == 0)
                        {
                            console.log("[Applet] Выполнено");
                            a = 1;
                        }
                        else
                        {
                            console.warn("[Applet] Не удалось выполнить печать чека ( программная ошибка )");
                            a = object.error_code;
                        }
					}
					else
						console.warn("[Applet] Не удалось выполнить печать чека ( аппаратная ошибка )");
				}					
				else
					console.warn("[Applet] Апплет не готов");

                return a;
			}, 
			cancel: function(){
				//if ([8,40,24,56].in_array(plugin.printer.mode))
				//{
					var answer = equipment_applet.printerCancelCheck();

					if (answer != -1){
						var object = JSON.parse(answer);
					if(object.error_code == 0)
						{console.log("[Applet] Выполнено");plugin.printer.status();plugin.printer.cut();}
					else
						console.warn("[Applet] Не удалось выполнить отмену чека, ошибка " + object.error_code + ' (' + plugin.printer.errors[object.error_code] + ')');
					}
					else
						console.warn("[Applet] Не удалось выполнить отмену чека ( аппаратная ошибка )");
				//}
				//else
				//	console.warn("[Applet] Апплет не готов");
			}, 
			printWithData: function(type, products, payments){
                var a = -1;
				if ([1,2,3].in_array(plugin.printer.mode))
				{
                    $('#status_span').text('Инициализация');
					this.init();
					this.setType(type);

                    $('#status_span').text('Обработка товаров');
					for(i = 0; i < products.length; i++)						
						this.addProduct(products[i].title, products[i].count, products[i].price);

                    $('#status_span').text('Обработка платежей');
					for(i = 0; i < payments.length; i++)
						this.addPayment(payments[i].index, payments[i].amount);

                    $('#status_span').text('Печать');
					a = this.print();

                    if (a == 1)
                        $('#status_span').text('Печать завершена');
                    else
                        $('#status_span').text('Во время печати возникла ошибка');

					plugin.printer.cut();
				}
				else
					console.warn("[Applet] Апплет не готов");

                return a;
			}
		}, 
		status: function(){
            var answer = -1;
			answer = equipment_applet.printerGetStatus();
			this.mode = 99;
			this.submode = 99;
            $('#status_span').text('Печать невозможна');
            $('.print_button').attr('disabled', 'disabled');

			if (answer != -1){
				var object = JSON.parse(answer);
				if(object.error_code == 0)
				{
					this.mode = object.mode;
					this.submode = object.submode;

                    if([1,2,3].in_array(this.mode))
                    {
                        $('#status_span').text('Готов к печати');
                        $('.print_button').removeAttr('disabled');
                    }
                    console.log("[Applet] Выполнено");
                    return object;
				}
				else
					console.warn("[Applet] Не удалось получить статус ( программная ошибка )");
			}
			else
				console.warn("[Applet] Не удалось получить статус ( аппаратная ошибка )");

			return -1;
		}, 
		modes: {
			99: "Принтер не подключен", 
			0: "Принтер в рабочем режиме.", 
			1: "Выдача данных.", 
			2: "Открытая смена, 24 часа не кончились.", 
			3: "Открытая смена, 24 часа кончились.", 
			4: "Закрытая смена.", 
			5: "Блокировка по неправильному паролю налогового инспектора.", 
			6: "Ожидание подтверждения ввода даты.", 
			7: "Разрешение изменения положения десятичной точки.", 
			8: "Открытый документ", 
			9: "Режим разрешения технологического обнуления. В этот режим ККМ переходит по включению питания, если некорректна информация в энергонезависимом ОЗУ ККМ.", 
			10: "Тестовый прогон.", 
			11: "Печать полного фис. отчета.", 
			12: "Печать отчёта ЭКЛЗ.", 
			13: "Работа с фискальным подкладным документом:",
			14: "Печать подкладного документа.", 
			15: "Фискальный подкладной документ сформирован.",  
			40: "Печать чека возврата продажи", 
			24: "Печать чека покупки", 
			56: "Печать чека возврата покупки"
		}, 
		submodes: {
			99: {
				99: "Принтер не подключен"
			},
			0: {
				0: "Подрежим не предусмотрен"
			}, 
			1: {
				0: "Подрежим не предусмотрен"
			}, 
			2: {
				0: "Подрежим не предусмотрен"
			}, 
			3: {
				0: "Подрежим не предусмотрен"
			}, 
			4: {
				0: "Подрежим не предусмотрен"
			},
			5: {
				0: "Подрежим не предусмотрен"
			},
			6: {
				0: "Подрежим не предусмотрен"
			},
			7: {
				0: "Подрежим не предусмотрен"
			},
			8: {
				0: "Продажа", 
				1: "Покупка", 
				2: "Возврат продажи", 
				3: "Возврат покупки"
			}, 
			9: {
				0: "Подрежим не предусмотрен"
			}, 
			10: {
				0: "Подрежим не предусмотрен"
			}, 
			11: {
				0: "Подрежим не предусмотрен"
			}, 
			12: {
				0: "Подрежим не предусмотрен"
			}, 
			13: {
				0: "Продажа (открыт)", 
				1: "Покупка (открыт)", 
				2: "Возврат продажи (открыт)", 
				3: "Возврат покупки (открыт)"
			}, 
			14: {
				0: "Ожидание загрузки.", 
				1: "Загрузка и позиционирование.", 
				2: "Позиционирование.", 
				3: "Печать.", 
				4: "Печать закончена.", 
				5: "Выброс документа.",
				6: "Ожидание извлечения."
			}, 
			15: {
				0: "Подрежим не предусмотрен"
			}
		},
        errors: {
            0: 'Ошибок нет',
            1: 'Неисправен накопитель ФП 1, ФП 2 или часы',
            2: 'Отсутствует ФП 1',
            3: 'Отсутствует ФП 2',
            4: 'Некорректные параметры в команде обращения к ФП',
            5: 'Нет запрошенных данных',
            6: 'ФП в режиме вывода данных',
            7: 'Некорректные параметры в команде для данной реализации ФП',
            8: 'Команда не поддерживается в данной реализации ФП',
            9: 'Некорректная длина команды',
            10: 'Формат данных не BCD',
            11: 'Неисправна ячейка памяти ФП при записи итога',
            17: 'Не введена лицензия',
            18: 'Заводской номер уже введен',
            19: 'Текущая дата меньше даты последней записи в ФП',
            20: 'Область сменных итогов ФП переполнена',
            21: 'Смена уже открыта',
            22: 'Смена не открыта',
            23: 'Номер первой смены больше номера последней смены',
            24: 'Дата первой смены больше даты последней смены',
            25: 'Нет данных в ФП',
            26: 'Область перерегистраций в ФП переполнена',
            27: 'Заводской номер не введен',
            28: 'В заданном диапазоне есть поврежденная запись',
            29: 'Повреждена последняя запись сменных итогов',
            30: 'Область перерегистраций ФП переполнена ',
            31: 'Отсутствует память регистров',
            32: 'Переполнение денежного регистра при добавлении',
            33: 'Вычитаемая сумма больше содержимого денежного регистра',
            34: 'Неверная дата',
            35: 'Нет записи активизации',
            36: 'Область активизаций переполнена',
            37: 'Нет активизации с запрашиваемым номером',
            38: 'Вносимая клиентом сумма меньше суммы чека',
            43: 'Невозможно отменить предыдущую команду',
            44: 'Обнулённая касса (повторное гашение невозможно)',
            45: 'Сумма чека по секции меньше суммы сторно',
            46: 'В ФР нет денег для выплаты',
            48: 'ФР заблокирован, ждет ввода пароля налогового инспектора',
            50: 'Требуется выполнение общего гашения',
            51: 'Некорректные параметры в команде',
            52: 'Нет данных',
            53: 'Некорректный параметр при данных настройках',
            54: 'Некорректные параметры в команде для данной реализации ФР',
            55: 'Команда не поддерживается в данной реализации ФР',
            56: 'Ошибка в ПЗУ',
            57: 'Внутренняя ошибка ПО ФР',
            58: 'Переполнение накопления по надбавкам в смене',
            59: 'Переполнение накопления в смене',
            60: 'Смена открыта – операция невозможна',
            61: 'Смена не открыта – операция невозможна',
            62: 'Переполнение накопления по секциям в смене',
            63: 'Переполнение накопления по скидкам в смене',
            64: 'Переполнение диапазона скидок',
            65: 'Переполнение диапазона оплаты наличными',
            66: 'Переполнение диапазона оплаты типом 2',
            67: 'Переполнение диапазона оплаты типом 3',
            68: 'Переполнение диапазона оплаты типом 4',
            69: 'Cумма всех типов оплаты меньше итога чека',
            70: 'Не хватает наличности в кассе',
            71: 'Переполнение накопления по налогам в смене',
            72: 'Переполнение итога чека',
            73: 'Операция невозможна в открытом чеке данного типа',
            74: 'Открыт чек – операция невозможна',
            75: 'Буфер чека переполнен',
            76: 'Переполнение накопления по обороту налогов в смене',
            77: 'Вносимая безналичной оплатой сумма больше суммы чека',
            78: 'Смена превысила 24 часа',
            79: 'Неверный пароль',
            80: 'Идет печать предыдущей команды',
            81: 'Переполнение накоплений наличными в смене',
            82: 'Переполнение накоплений по типу оплаты 2 в смене',
            83: 'Переполнение накоплений по типу оплаты 3 в смене',
            84: 'Переполнение накоплений по типу оплаты 4 в смене',
            85: 'Чек закрыт – операция невозможна',
            86: 'Нет документа для повтора',
            87: 'ЭКЛЗ: количество закрытых смен не совпадает с ФП',
            88: 'Ожидание команды продолжения печати',
            89: 'Документ открыт другим оператором',
            90: 'Скидка превышает накопления в чеке',
            91: 'Переполнение диапазона надбавок',
            92: 'Понижено напряжение 24В',
            93: 'Таблица не определена',
            94: 'Некорректная операция',
            95: 'Отрицательный итог чека',
            96: 'Переполнение при умножении',
            97: 'Переполнение диапазона цены',
            98: 'Переполнение диапазона количества',
            99: 'Переполнение диапазона отдела',
            100: 'ФП отсутствует',
            101: 'Не хватает денег в секции',
            102: 'Переполнение денег в секции',
            103: 'Ошибка связи с ФП',
            104: 'Не хватает денег по обороту налогов',
            105: 'Переполнение денег по обороту налогов',
            106: 'Ошибка питания в момент ответа по I2C',
            107: 'Нет чековой ленты',
            108: 'Нет контрольной ленты',
            109: 'Не хватает денег по налогу',
            110: 'Переполнение денег по налогу',
            111: 'Переполнение по выплате в смене',
            112: 'Переполнение ФП',
            113: 'Ошибка отрезчика',
            114: 'Команда не поддерживается в данном подрежиме',
            115: 'Команда не поддерживается в данном режиме',
            116: 'Ошибка ОЗУ',
            117: 'Ошибка питания',
            118: 'Ошибка принтера: нет импульсов с тахогенератора',
            119: 'Ошибка принтера: нет сигнала с датчиков',
            120: 'Замена ПО',
            121: 'Замена ФП',
            122: 'Поле не редактируется',
            123: 'Ошибка оборудования',
            124: 'Не совпадает дата',
            125: 'Неверный формат даты',
            126: 'Неверное значение в поле длины',
            127: 'Переполнение диапазона итога чека',
            128: 'Ошибка связи с ФП',
            129: 'Ошибка связи с ФП',
            130: 'Ошибка связи с ФП',
            131: 'Ошибка связи с ФП',
            132: 'Переполнение наличности',
            133: 'Переполнение по продажам в смене',
            134: 'Переполнение по покупкам в смене',
            135: 'Переполнение по возвратам продаж в смене',
            136: 'Переполнение по возвратам покупок в смене',
            137: 'Переполнение по внесению в смене',
            138: 'Переполнение по надбавкам в чеке',
            139: 'Переполнение по скидкам в чеке',
            140: 'Отрицательный итог надбавки в чеке',
            141: 'Отрицательный итог скидки в чеке',
            142: 'Нулевой итог чека',
            143: 'Касса не фискализирована',
            144: 'Поле превышает размер, установленный в настройках',
            145: 'Выход за границу поля печати при данных настройках шрифта',
            146: 'Наложение полей',
            147: 'Восстановление ОЗУ прошло успешно',
            148: 'Исчерпан лимит операций в чеке',
            160: 'Ошибка связи с ЭКЛЗ',
            161: 'ЭКЛЗ отсутствует',
            162: 'ЭКЛЗ: Некорректный формат или параметр команды',
            163: 'Некорректное состояние ЭКЛЗ',
            164: 'Авария ЭКЛЗ',
            165: 'Авария КС в составе ЭКЛЗ',
            166: 'Исчерпан временной ресурс ЭКЛЗ',
            167: 'ЭКЛЗ переполнена',
            168: 'ЭКЛЗ: Неверные дата и время',
            169: 'ЭКЛЗ: Нет запрошенных данных',
            170: 'Переполнение ЭКЛЗ (отрицательный итог документа)',
            176: 'ЭКЛЗ: Переполнение в параметре количество',
            177: 'ЭКЛЗ: Переполнение в параметре сумма',
            178: 'ЭКЛЗ: Уже активизирована',
            192: 'Контроль даты и времени (подтвердите дату и время)',
            193: 'ЭКЛЗ: суточный отчёт с гашением прервать нельзя',
            194: 'Превышение напряжения в блоке питания',
            195: 'Несовпадение итогов чека и ЭКЛЗ',
            196: 'Несовпадение номеров смен',
            197: 'Буфер подкладного документа пуст',
            198: 'Подкладной документ отсутствует',
            199: 'Поле не редактируется в данном режиме',
            200: 'Отсутствуют импульсы от таходатчика'
        }
	}, 
	bank: {
        income: function(amount){
            var answer = -1;
            answer = equipment_applet.posIncome(amount);

            if (answer != -1)
            {
                var object = JSON.parse(answer);
                console.log('[Applet] Получен ответ от терминала.');
                var status = parseInt(object.answer[0]);

                if (status == 0)
                {
                    console.log('[Applet] Внос денег успешен. Внутренний номер транзакции : ');
                    console.log(object.answer[4]);
                    console.log('[Applet] Номер карты клиента : ');
                    console.log(object.answer[1]);
                    console.log('[Applet] Тип карты клиента : ');
                    console.log(object.answer[5]);

                    plugin.printer.status();
                    plugin.printer.cut();
                }
                else
                    console.warn('[Applet] Не удалось внести деньги ');
            }
            else
                console.warn('[Applet] Не удалось внести деньги (аппаратная ошибка)');


        },
        outcome: function(amount){
            var answer = -1;
            answer = equipment_applet.posOutcome(amount);

            if (answer != -1)
            {
                var object = JSON.parse(answer);
                console.log('[Applet] Получен ответ от терминала.');
                var status = parseInt(object.answer[0]);

                if (status == 0)
                {
                    console.log('[Applet] Отмена операции успешна. Внутренний номер транзакции : ');
                    console.log(object.answer[4]);
                    console.log('[Applet] Номер карты клиента : ');
                    console.log(object.answer[1]);
                    console.log('[Applet] Тип карты клиента : ');
                    console.log(object.answer[5]);

                    plugin.printer.status();
                    plugin.printer.cut();
                }
                else
                    console.warn('[Applet] Не удалось отменить операцию');
            }
            else
                console.warn('[Applet] Не удалось отменить операцию (аппаратная ошибка)');
        },
		hello: function(){
			alert('Hello');
		}
	}
}
