import styled from "styled-components";
import { useState } from "react";

import "App.css";

/**
 * @explain 1. 결과를 나타내기 위한 div
 */
const FirstResult = styled.div`
  font-size: 25px;
  margin: 0px 0px 15px 15px;
`;

/**
 * @explain 2. 영역별 상세 정보를 나타내기 위한 div
 */
const SecondResult = styled(FirstResult)`
  margin: 20px 0px 15px 15px;
`;

/**
 * @explain 영역 별 점수와 그래프를 나타내는 헤더
 */
const ResultOfHeader = styled.div`
  display: flex;
  border-bottom: 2px solid #dbe2ef;
  margin: 0px 0px 0px 15px;
`;

/**
 * @explain Writing 결과
 */
const ResultOfWriting = styled.div`
  display: flex;
  padding: 15px 0px 10px 0px;
  border-bottom: 2px solid #dbe2ef;
  margin: 0px 0px 0px 15px;
`;
const ResultOfListening = styled(ResultOfWriting)``;
const ResultOfSpeaking = styled(ResultOfWriting)``;
const ResultOfReading = styled(ResultOfWriting)``;

/**
 * @explain 어떤 영역의 결과인지를 알려주는 div
 */
const ResultName = styled.div`
  font-family: Arial;
  width: 100px;
`;

/**
 * @explain 평가 내용을 나타내는 div
 */

const ResultEvaluationDetail = styled.div``;

const Result2 = (prop) => {
  const [result, setResult] = useState(prop.result);

  return (
    <>
      <SecondResult>2. 영역별 상세 정보</SecondResult>
      <ResultOfHeader>
        <ResultName>분야</ResultName>
        <ResultEvaluationDetail>평가 내용</ResultEvaluationDetail>
      </ResultOfHeader>
      <ResultOfWriting>
        <ResultName>Writing</ResultName>
        <ResultEvaluationDetail>~~ 가 부족합니다.</ResultEvaluationDetail>
      </ResultOfWriting>
      <ResultOfListening>
        <ResultName>Listening</ResultName>
        <ResultEvaluationDetail>~~ 가 뛰어 납니다.</ResultEvaluationDetail>
      </ResultOfListening>
      <ResultOfSpeaking>
        <ResultName>Speaking</ResultName>
        <ResultEvaluationDetail>
          ~~ 가 필요해 보입니다~~ 가 필요해 보입니다.
        </ResultEvaluationDetail>
      </ResultOfSpeaking>
      <ResultOfReading>
        <ResultName>Reading</ResultName>
        <ResultEvaluationDetail>
          ~~ 하는 것을 추천합니다.
        </ResultEvaluationDetail>
      </ResultOfReading>
    </>
  );
};

export default Result2;
