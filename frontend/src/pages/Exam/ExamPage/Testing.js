import styled from "styled-components";
import { useState } from "react";

import "App.css";

import test from "db/exam_dummy/question_dummy.json";

const Main = styled.div`
  width: 100%;
  height: calc(100% - 80px);
  font-family: SCDream5;
`;

const Wrapper = styled.div`
  box-sizing: border-box;
  width: 1080px;
  margin: 0 auto;
`;

const PartTitle = styled.div`
  box-sizing: border-box;
  margin-top: 50px;
  font-size: 40px;
  border-bottom: 5px solid grey;
`;

const ExamQuestion = styled.div`
  box-sizing: border-box;
  font-size: 35px;
  margin-top: 20px;
`;
const ExamMainText = styled(ExamQuestion)`
  font-size: 30px;
`;
const ExamPicture = styled.img.attrs((prop) => ({
  src: prop.imgSrc,
  alt: "test",
}))`
  width: 400px;
  height: 225px;
`;

const Testing = () => {
  console.log(test);

  /**
   * @explain 몇번 째 시험 문제인지 표시
   */
  const [examNum, setExamNum] = useState(1);

  return (
    <Main>
      <Wrapper>
        <PartTitle>Part : Speaking</PartTitle>
        <ExamQuestion>{test.question[0].q}</ExamQuestion>
        <ExamMainText>{test.question[0].mainText}</ExamMainText>
      </Wrapper>
    </Main>
  );
};

export default Testing;
