import styled from "styled-components";
import { useState } from "react";
import { produce } from "immer";
import { useNavigate } from "react-router-dom";

const Main = styled.div`
  width: 1000px;
  margin: 0 auto;
  font-family: SCDream5;
  font-size: 16px;
`;

const TitleDiv = styled.div`
  box-sizing: border-box;
  text-align: center;
  margin-top: 30px;
`;
const TitleSpanBlack = styled.span`
  font-size: 75px;
  font-family: SCDream9;
`;
const TitleSpanRed = styled(TitleSpanBlack)`
  color: #ff0000;
`;

const BtnGrid = styled.div`
  display: grid;
  width: 700px;
  margin: 20px auto;
  grid-template-columns: 150px 150px 150px 150px;
  justify-content: space-between;
`;

const CurriculumBtn1 = styled.button`
  box-sizing: border-box;
  border: none;
  border-radius: 10px;
  width: 150px;
  height: 50px;
  font-family: SCDream5;
  cursor: pointer;
  font-size: 20px;
  background-color: ${(prop) =>
    prop.isSelect === "all" ? "#35a753" : "#f0f5f9"};
  color: black;
`;
const CurriculumBtn2 = styled(CurriculumBtn1)`
  background-color: ${(prop) =>
    prop.isSelect === "reading" ? "#35a753" : "#f0f5f9"};
`;
const CurriculumBtn3 = styled(CurriculumBtn1)`
  background-color: ${(prop) =>
    prop.isSelect === "speaking" ? "#35a753" : "#f0f5f9"};
`;
const CurriculumBtn4 = styled(CurriculumBtn1)`
  background-color: ${(prop) =>
    prop.isSelect === "english" ? "#35a753" : "#f0f5f9"};
`;

const Schedule = styled.div``;
const SelectDate = styled.div`
  display: flex;
  width: 700px;
  margin: 15px auto;
  align-items: center;
`;
const SelectTime = styled(SelectDate)``;
const SelectMonth = styled(SelectDate)``;

const DateSpan = styled.span`
  height: 50px;
  line-height: 50px;
  width: 170px;
  text-align: left;
`;
const DateBtnDiv = styled.div`
  display: flex;
  width: 500px;
  justify-content: space-between;
`;
const DateBtn = styled.button`
  box-sizing: border-box;
  border: none;
  border-radius: 10px;
  width: 60px;
  height: 40px;

  cursor: pointer;
  font-size: 20px;
  background-color: ${(prop) => (prop.val === true ? "#35a753" : "#f0f5f9")};
`;

const TimeSpan = styled(DateSpan)``;
const MonthSpan = styled(DateSpan)``;

const TimeInput = styled.input.attrs({
  placeholder: "시간",
  required: true,
  type: "number",
})`
  box-sizing: border-box;
  padding-left: 15px;
  width: 400px;
  height: 40px;
  font-size: 18px;
  border: 0;
  border-radius: 15px;
  background-color: #f0f5f9;

  ::placeholder {
    font-size: 16px;
    font-family: SCDream5;
  }
  :focus {
    outline: 2px solid black;
  }
`;
const MonthInput = styled(TimeInput).attrs(() => ({
  placeholder: "개월 수",
}))``;

const SaveBtn = styled.button`
  box-sizing: border-box;
  width: 100px;
  height: 40px;
  border: none;
  border-radius: 15px;
  font-size: 15px;
  color: white;
  background-color: #1e1d4d;
  text-decoration: none;
  &:hover {
    cursor: pointer;
  }
`;

const ServeyBeforeResult = () => {
  const navigate = useNavigate();

  const [selectInfo, setSelectInfo] = useState({
    content: "",
    date: {
      Mon: false,
      Tue: false,
      Wen: false,
      Thu: false,
      Fri: false,
      Sat: false,
      Sun: false,
    },
    time: 0,
    month: 0,
  });

  // 커리큘럼 타입 정하는 함수
  const curriculumType = (e) => {
    const { value } = e.target;

    setSelectInfo(
      produce((draft) => {
        draft.content = value;
      })
    );
  };

  // 수업 횟수, 수업 타임 수, 총 수강 개월 수 변경하는 함수
  const isClickDate = (e) => {
    const { tagName, innerText, value, name } = e.target;
    // 요일 선택시
    if (tagName === "BUTTON") {
      setSelectInfo(
        produce((draft) => {
          draft.date[innerText] = !draft.date[innerText];
        })
      );
    }
    // 수업 타임, 개월 수 변경시
    else if (tagName === "INPUT") {
      setSelectInfo(
        produce((draft) => {
          draft[name] = value;
        })
      );
    }
  };

  // 결과 페이지로 이동하는 함수
  const goResultPage = () => {
    navigate("/result");
  };

  return (
    <Main>
      <TitleDiv>
        <TitleSpanRed>JK&nbsp;</TitleSpanRed>
        <TitleSpanBlack>curriculum guide</TitleSpanBlack>
        <div>추천받고 싶은 커리큘럼 타입은?</div>
        <BtnGrid>
          <CurriculumBtn1
            onClick={curriculumType}
            value="all"
            isSelect={selectInfo.content}
          >
            통합 영어
          </CurriculumBtn1>
          <CurriculumBtn2
            onClick={curriculumType}
            value="reading"
            isSelect={selectInfo.content}
          >
            읽기 강화
          </CurriculumBtn2>
          <CurriculumBtn3
            onClick={curriculumType}
            value="speaking"
            isSelect={selectInfo.content}
          >
            {" "}
            말하기 / 쓰기 강화
          </CurriculumBtn3>
          <CurriculumBtn4
            onClick={curriculumType}
            value="english"
            isSelect={selectInfo.content}
          >
            미국교과서 프로그램
          </CurriculumBtn4>
        </BtnGrid>
        <Schedule>
          <SelectDate>
            <DateSpan>1주 수업 횟수</DateSpan>
            <DateBtnDiv>
              <DateBtn val={selectInfo.date.Mon} onClick={isClickDate}>
                Mon
              </DateBtn>
              <DateBtn val={selectInfo.date.Tue} onClick={isClickDate}>
                Tue
              </DateBtn>
              <DateBtn val={selectInfo.date.Wen} onClick={isClickDate}>
                Wen
              </DateBtn>
              <DateBtn val={selectInfo.date.Thu} onClick={isClickDate}>
                Thu
              </DateBtn>
              <DateBtn val={selectInfo.date.Fri} onClick={isClickDate}>
                Fri
              </DateBtn>
              <DateBtn val={selectInfo.date.Sat} onClick={isClickDate}>
                Sat
              </DateBtn>
              <DateBtn val={selectInfo.date.Sun} onClick={isClickDate}>
                Sun
              </DateBtn>
            </DateBtnDiv>
          </SelectDate>
          <SelectTime>
            <TimeSpan>1회당 수업 타임 수</TimeSpan>
            <TimeInput
              val={selectInfo.time}
              onChange={isClickDate}
              name="time"
            ></TimeInput>
          </SelectTime>
          <SelectMonth>
            <MonthSpan>총 수강 개월 수</MonthSpan>
            <MonthInput
              val={selectInfo.month}
              onChange={isClickDate}
              name="month"
            ></MonthInput>
          </SelectMonth>
        </Schedule>
        <SaveBtn onClick={goResultPage}>Save</SaveBtn>
      </TitleDiv>
    </Main>
  );
};

export default ServeyBeforeResult;
