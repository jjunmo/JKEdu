import styled from "styled-components";
import { useState } from "react";
import MakeTimeSchedule from "./MakeTimeSchedule";

const WrapperDiv = styled.div`
  width: 90%;
  margin: 15px 0 0 15px;
`;

const WeekInput = styled.input.attrs(() => {})`
  width: 60px;
  height: 32px;
  font-size: 16px;
  border: 0;
  border-radius: 15px;
  padding-left: 15px;
  box-sizing: border-box;
  background-color: #cfd2cf;

  ::placeholder {
    font-family: jua;
    font-size: 14px;
  }
`;

const SendButton = styled.button`
  border: none;
  border-radius: 15px;
  font-size: 15px;
  background-color: #97d2ec;
  margin: 0 0 0 15px;
  text-decoration: none;
  color: black;
  &:hover {
    cursor: pointer;
  }
`;

const DayWeekButton = styled(SendButton)`
  background-color: ${(prop) => (prop.value === false ? "#cfd2cf" : "#CDF0EA")};
`;

const TimeSchedule_origin = () => {
  /**
   * @explain 선택 날짜를 저장하는 state
   */
  const [scheduleInfo, setScheduleInfo] = useState({
    month: "",
    day: "",
    hour: "",
    date: {
      Mon: false,
      Tue: false,
      Wen: false,
      Thu: false,
      Fri: false,
      Sat: false,
      Sun: false,
    },
  });

  /**
   * @explain 입력 완료를 눌렀는지 확인하는 변수
   */
  const [isInputComplete, setIsInputComplete] = useState(false);

  /**
   * @explain 날짜를 선택하는 함수
   */
  const changeSchedule = (e) => {
    const { name, tagName, value } = e.target;

    // 수업 요일을 변경하는 것이라면
    if (tagName === "BUTTON") {
      setScheduleInfo({
        ...scheduleInfo,
        date: { ...scheduleInfo.date, [name]: !scheduleInfo.date[name] },
      });
    }
    // 주 몇회, 몇 시간, 몇 개월을 변경하는 거라면
    else if (tagName === "INPUT") {
      setScheduleInfo({
        ...scheduleInfo,
        [name]: value,
      });
    }
    // setDate({ ...scheduleInfo, [name]: !dateInfo[name] });
  };

  // 스케쥴 입력 후 입력완료를 누르면 실행하는 함수
  const sendInfo = () => {
    setIsInputComplete(true);
  };

  return (
    <WrapperDiv>
      <span>주</span>
      <WeekInput
        type="number"
        onChange={changeSchedule}
        value={scheduleInfo.day}
        name="day"
      ></WeekInput>
      <span>회, </span>
      <span>회당 </span>
      <WeekInput
        type="number"
        onChange={changeSchedule}
        value={scheduleInfo.hour}
        name="hour"
      ></WeekInput>
      <span>(시간), </span>
      <WeekInput
        type="number"
        onChange={changeSchedule}
        value={scheduleInfo.month}
        name="month"
      ></WeekInput>
      <span>개월</span>
      <span>&nbsp;&nbsp;&nbsp; 요일 선택</span>
      <DayWeekButton
        onClick={changeSchedule}
        name="Mon"
        value={scheduleInfo.date.Mon}
      >
        월
      </DayWeekButton>
      <DayWeekButton
        onClick={changeSchedule}
        name="Tue"
        value={scheduleInfo.date.Tue}
      >
        화
      </DayWeekButton>
      <DayWeekButton
        onClick={changeSchedule}
        name="Wen"
        value={scheduleInfo.date.Wen}
      >
        수
      </DayWeekButton>
      <DayWeekButton
        onClick={changeSchedule}
        name="Thu"
        value={scheduleInfo.date.Thu}
      >
        목
      </DayWeekButton>
      <DayWeekButton
        onClick={changeSchedule}
        name="Fri"
        value={scheduleInfo.date.Fri}
      >
        금
      </DayWeekButton>
      <DayWeekButton
        onClick={changeSchedule}
        name="Sat"
        value={scheduleInfo.date.Sat}
      >
        토
      </DayWeekButton>
      <DayWeekButton
        onClick={changeSchedule}
        name="Sun"
        value={scheduleInfo.date.Sun}
      >
        일
      </DayWeekButton>

      <SendButton onClick={sendInfo}>입력 완료</SendButton>
      <h2>시간표</h2>
      {isInputComplete ? <MakeTimeSchedule scheduleInfo={scheduleInfo} /> : ""}
      <div></div>
    </WrapperDiv>
  );
};

export default TimeSchedule_origin;
