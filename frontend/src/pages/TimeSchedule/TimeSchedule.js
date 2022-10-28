import styled from "styled-components";
import { useState } from "react";

const Main = styled.div`
  width: 100rem;
  margin: 0 auto;

  font-size: 1.6rem;
  font-family: SCDream5;
`;
const Header = styled.div`
  display: flex;
  justify-content: space-between;
  height: 6rem;
`;
const Title = styled.div`
  font-family: SCDream9;
  font-size: 3rem;
  line-height: 6rem;
`;
// 선택된 날짜
const SelectedDate = styled.div`
  display: flex;
  align-items: center;
`;
const DateBtn = styled.button`
  width: 3rem;
  height: 3rem;
  margin: 0rem 1rem;
  padding: 0rem;

  border: none;
  border-radius: 0.5rem;
  background-color: ${(prop) =>
    prop.isSelect === true ? "#35a753" : "#D8D9CF"};

  cursor: pointer;
`;

const Explain = styled.div`
  height: 20rem;
  box-sizing: border-box;
  border: 0.2rem solid black;
`;

const Schedule = styled.div`
  box-sizing: border-box;
  display: grid;
  grid-template-columns: 1fr;
  margin: 2rem auto;

  border: 0.2rem solid black;
  border-right: none;
`;

const ScheduleHeader = styled.div`
  box-sizing: border-box;
  height: 6rem;
  line-height: 6rem;
  font-family: SCDream9;
  font-size: 3rem;
  text-align: center;

  border-right: 0.2rem solid black;
`;
const NthSchedule = styled.div`
  box-sizing: border-box;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
`;
const ScheduleDate = styled(ScheduleHeader)`
  box-sizing: border-box;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  font-family: SCDream5;
  font-size: 2rem;
  border-top: 0.2rem solid black;
`;
const ScheduleList = styled(ScheduleHeader)`
  box-sizing: border-box;
  font-size: 2rem;
  font-family: SCDream5;
  border-top: 0.2rem solid black;
  border-right: 0.2rem solid black;
  background-color: ${(prop) => prop.bgColor};
`;
const SchduleTime = styled(ScheduleList)`
  box-sizing: border-box;
  font-family: SCDream5;
`;

const TimeSchedule = () => {
  const [selectDate, setSelectDate] = useState({
    Mon: true,
    Tue: false,
    Wen: true,
    Thu: false,
    Fri: true,
  });

  return (
    <Main>
      <Header>
        <Title>추천 시간표</Title>
        <SelectedDate>
          <DateBtn isSelect={selectDate.Mon}>월</DateBtn>
          <DateBtn isSelect={selectDate.Tue}>화</DateBtn>
          <DateBtn isSelect={selectDate.Wen}>수</DateBtn>
          <DateBtn isSelect={selectDate.Thu}>목</DateBtn>
          <DateBtn isSelect={selectDate.Fri}>금</DateBtn>
        </SelectedDate>
      </Header>
      <Explain>
        현재 *** 학생은 Speaking 영역에서 같은 학년 학생들보다 우수한 성적을
        보이고 있고, Reading, Writing 영역은 그에 비해서 다소 취약합니다. 따라서
        Reading, Writing 중점의 수업을 추천합니다. 추천 시간표는 이전 질문에
        대한 응답을 바탕으로 제작하였으며, 우측 상단의 버튼으로 요일 변경이
        가능합니다.
      </Explain>
      <Schedule>
        <ScheduleHeader>***'s Schedule</ScheduleHeader>
        <ScheduleDate>
          <div></div>
          <div>월</div>
          <div>수</div>
          <div>금</div>
        </ScheduleDate>
        <NthSchedule>
          <SchduleTime>1</SchduleTime>
          <ScheduleList bgColor="#CCD2F0">Speaking</ScheduleList>
          <ScheduleList bgColor="#DDECCA">Writing</ScheduleList>
          <ScheduleList bgColor="#FEEBB6">Grammar</ScheduleList>
        </NthSchedule>
        <NthSchedule>
          <SchduleTime>2</SchduleTime>
          <ScheduleList bgColor="#DDECCA">Writing</ScheduleList>
          <ScheduleList bgColor="#B8E9FF">Listening</ScheduleList>
          <ScheduleList bgColor="#FECCBE">Reading</ScheduleList>
        </NthSchedule>
        <NthSchedule>
          <SchduleTime>3</SchduleTime>
          <ScheduleList bgColor="#FEEBB6">Grammar</ScheduleList>
          <ScheduleList bgColor="#FECCBE">Reading</ScheduleList>
          <ScheduleList bgColor="#DDECCA">Writing</ScheduleList>
        </NthSchedule>
      </Schedule>
    </Main>
  );
};

export default TimeSchedule;
