// 응시 영역 선택하는 컴포넌트

import { useNavigate } from "react-router-dom";
import styled from "styled-components";

import "App.css";

const Main = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: calc(100% - 80px);
  font-family: SCDream5;
`;

const Wrapper = styled.div`
  width: 1000px;
`;

const LevelDiv = styled.div`
  /* display: flex;
  justify-content: space-between; */

  display: grid;
  grid-template-columns: 180px 180px 180px 180px 180px;
  justify-content: space-between;

  @media screen and (max-width: 768px) {
    grid-template-columns: 180px 180px 180px;
  }
`;

const WritingButton = styled.button`
  box-sizing: border-box;
  border: none;
  padding: 0px;
  margin: 0;
  width: 180px;
  height: 90px;
  color: white;
  font-family: SCDream5;
  font-size: 24px;
  border-radius: 10px;
  border: none;
  background-color: #35a753;
  cursor: pointer;
`;
const ReadingButton = styled(WritingButton)``;
const ListeningButton = styled(WritingButton)``;
const SpeakingButton = styled(WritingButton)``;
const GrammarButton = styled(WritingButton)``;

const ResultDiv = styled.div`
  text-align: center;
  margin-top: 25px;
`;
const ResultButton = styled(WritingButton)`
  width: 220px;
  background-color: #fd9f28;
`;

const SelectExam = () => {
  const navi = useNavigate();

  const isSelectTest = (test) => {
    if (window.confirm(`${test}시험을 시작할까요?`)) {
      navi(`/exam/${test}`);
    } else {
    }
  };

  const goResultPage = () => {
    navi("/servey");
  };

  return (
    <Main>
      <Wrapper>
        <h1 style={{ textAlign: "center" }}>시험 영역을 선택하세요.</h1>
        <LevelDiv>
          <SpeakingButton onClick={() => isSelectTest("Speaking")}>
            Speaking
          </SpeakingButton>
          <ReadingButton onClick={() => isSelectTest("Reading")}>
            Reading
          </ReadingButton>
          <ListeningButton onClick={() => isSelectTest("Listening")}>
            Listening
          </ListeningButton>
          <GrammarButton onClick={() => isSelectTest("Grammar")}>
            Grammar
          </GrammarButton>
          <WritingButton onClick={() => isSelectTest("Writing")}>
            Writing
          </WritingButton>
        </LevelDiv>
        <ResultDiv>
          <ResultButton onClick={goResultPage}>결과보기</ResultButton>
        </ResultDiv>
      </Wrapper>
    </Main>
  );
};
export default SelectExam;
