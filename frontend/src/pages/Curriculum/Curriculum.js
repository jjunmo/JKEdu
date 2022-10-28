import styled from "styled-components";
import { useNavigate } from "react-router-dom";

import "App.css";
import SpeakingBookGuide from "./SpeakingBookGuide";
import ReadingBookGuide from "./ReadingBookGuide";
import WritingBookGuide from "./WritingBookGuide";
import ListeningBookGuide from "./ListeningBookGuide";
import GrammarBookGuide from "./GrammarBookGuide";

const Wrapper = styled.div`
  width: 1000px;
  margin: 15px auto;

  font-family: SCDream5;
  font-size: 16px;
`;

const Header = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const Title = styled.div`
  display: inline;
  font-size: 30px;
  margin: 0;
`;

const BtnWrapper = styled.div``;

const CurriculumBtn = styled.button`
  box-sizing: border-box;
  border: none;
  width: 70px;
  height: 35px;
  margin-right: 15px;

  background-color: #35a753;
  color: white;
  border-radius: 15px;
  font-size: 15px;
  font-family: SCDream5;

  cursor: pointer;
`;
const ScheduleBtn = styled(CurriculumBtn)`
  width: 70px;
  height: 40px;
`;

const Explain = styled.div`
  box-sizing: border-box;
  border: 2px solid black;
  height: 100px;
  margin: 20px 0;
`;

const Curriculum = () => {
  const navigate = useNavigate();

  return (
    <Wrapper>
      <Header>
        <Title>커리큘럼 추천</Title>
        <BtnWrapper>
          <CurriculumBtn>3개월</CurriculumBtn>
          <CurriculumBtn>6개월</CurriculumBtn>
          <CurriculumBtn>12개월</CurriculumBtn>
        </BtnWrapper>
      </Header>
      <Explain>
        추천 커리큘럼은 이전 질문에 대한 응답을 바탕으로 제작하였으며, 우측
        상단의 버튼으로 기간 변경이 가능합니다. 책 이미지에 마우스를 대면 해당
        책 설명을 볼 수 있고, 클릭시 구매 페이지로 이동합니다.
      </Explain>
      <SpeakingBookGuide />
      <ReadingBookGuide />
      <ListeningBookGuide />
      <GrammarBookGuide />
      <WritingBookGuide />
      <ScheduleBtn
        onClick={() => {
          navigate("/time-schedule");
        }}
      >
        시간표
      </ScheduleBtn>
    </Wrapper>
  );
};

export default Curriculum;
