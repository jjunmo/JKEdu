import styled from "styled-components";

const Schedule = styled.div`
  box-sizing: border-box;
  width: 810px;
  border: 5px solid #dcd6f7;
`;

// 스케줄표 헤더(월, 화, 수, 목, 금, 토, 일을 나타냄)
const ScheduleHeader = styled.div`
  display: flex;
  text-align: center;

  > :nth-child(8) {
    border-right: none;
  }
`;

const ScheduleDate = styled.div`
  box-sizing: border-box;
  width: 100px;
  border-right: 2px solid #a6b1e1;
  border-bottom: 2px solid #a6b1e1;
`;

const TimeToDo = styled.div`
  box-sizing: border-box;
  height: 80px;
  width: 800px;
  text-align: center;
  border-bottom: 1px solid #a6b1e1;
  align-items: center;
  display: flex;
`;

const MakeTimeSchedule = (prop) => {
  // TimeSchedule에서 넘겨준 dateInfo
  // ai기반이면 필요할지 안 필요할지 모르겠음
  const scheduleInfo = prop.scheduleInfo;

  console.log(scheduleInfo);

  const makeSchedules = () => {
    const sche = [];

    for (let i = 0; i < Number(scheduleInfo.day); i++) {
      sche.push(
        <TimeToDo>
          <div style={{ width: "100px" }}>{i + 1}</div>
          <div style={{ width: "100px" }}>Spekaing</div>
          <div style={{ width: "100px" }}></div>
          <div style={{ width: "100px" }}>Writing</div>
          <div style={{ width: "100px" }}></div>
          <div style={{ width: "100px" }}>Listening</div>
          <div style={{ width: "100px" }}></div>
          <div style={{ width: "100px" }}></div>
        </TimeToDo>
      );
    }
    return sche;
  };

  return (
    <Schedule>
      <ScheduleHeader>
        <ScheduleDate></ScheduleDate>
        <ScheduleDate>월</ScheduleDate>
        <ScheduleDate>화</ScheduleDate>
        <ScheduleDate>수</ScheduleDate>
        <ScheduleDate>목</ScheduleDate>
        <ScheduleDate>금</ScheduleDate>
        <ScheduleDate>토</ScheduleDate>
        <ScheduleDate>일</ScheduleDate>
      </ScheduleHeader>
      {makeSchedules()}
    </Schedule>
  );
};

export default MakeTimeSchedule;
