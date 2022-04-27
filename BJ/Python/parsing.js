let monthDay = new Array(12); //1월은 그냥 , 2월 부터 0번째를
let year = 2021;
let record = new Array(365);
let newDate
for(let i = 0; i < 12; i++){
    monthDay[i] = 0;
}
for(let i = 1; i < 12; i++){
    let newDate = new Date(year , i , 0);
    monthDay[i] = monthDay[i - 1] + newDate.getDate();
}
for(let i = 0; i < 365; i++){
    record[i] = 0;
}
let dateTime = new Date(2021 , 10 , 30 , 9 , 0 , 0); //실제로는 11월을 입력한 것과 같음 9시로 한 이유는 자꾸 9시간 전으로 되서
record[monthDay[dateTime.getMonth()] + dateTime.getDate() - 1] += 1;
let monthDayIndex = 0;
let month = 1;
for(let i = 0; i < record.length; i++){
    let count = record[i];
    let decodeDay = i + 1;
    if(monthDayIndex != 11 && decodeDay > monthDay[monthDayIndex + 1]){
        monthDayIndex++;
        month++;
    }
    let innerDate = new Date(year , month - 1, decodeDay - monthDay[monthDayIndex] ,0 , 0 , 0);
    console.log("날짜 : " + innerDate.getFullYear() + " " + (innerDate.getMonth() + 1) + " " + innerDate.getDate() + " 필사 쓴 수 : " + count);
}