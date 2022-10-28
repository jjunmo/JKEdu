import { useState, useRef } from "react";
import styled from "styled-components";
import ReactToPrint from "react-to-print";

import ResultHeader from "./ResultHeader";
import Result1Privacy from "./Result1Privacy";
import Result2DetailEvaluation from "./Result2DetailEvaluation";
import Result3RadarChart from "./Result3RadarChart";
import Result3HorizonGraph from "./Result3HorizonGraph";
import Result4Graph from "./Result4Graph";
import Result4CEFRExplain from "./Result4CEFRExplain";

import "App.css";

const Main = styled.div`
  box-sizing: border-box;
  width: 1000px;
  max-width: 1000px;
  margin: 50px auto;
  font-family: SCDream5;
  font-size: 16px;
  @media screen and (max-width: 768px) {
    width: 100%;
  }
`;

const Wrapper = styled.div``;

const Content = styled.div``;

// 인쇄를 위한 div
const Print = styled.div`
  margin: 15px auto;

  @media print {
    display: none;
  }

  // 모바일에서는 인쇄버튼 안보이게
  @media screen and (max-width: 768px) {
    display: none;
  }
`;

const PrintBtn = styled.button`
  width: 19rem;
  max-width: 19rem;
  height: 5.5rem;
  border: none;

  border-radius: 5rem;
  font-size: 2rem;
  background-color: #1e1d4d;
  text-decoration: none;
  color: white;
  cursor: pointer;
`;

const CEFR = styled.div`
  @media print {
    padding-top: 30px;
    page-break-inside: avoid;
    page-break-after: auto;
  }
`;

const ResultGraph = styled.div`
  margin-top: 20px;
`;

const GraphDisplay = styled.div`
  display: flex;
  justify-content: space-between;
  @media screen and (max-width: 768px) {
    display: block;
  }
`;

const CEFRDisplay = styled(GraphDisplay)``;

const Name = styled.div`
  font-size: 25px;
  font-family: SCDream9;
  margin: 15px auto;

  @media screen and (max-width: 768px) {
    width: 90%;
    font-size: 15px;
  }
`;

const Result = () => {
  // 프린트할 영역을 선택하기 위한 ref 변수
  const printRef = useRef();

  // answer : 정답 수, problem : 문제 수
  const [result, setResult] = useState({
    Writing: {
      answer: 5,
      problem: 6,
      level: "B2+",
    },
    Listening: {
      answer: 15,
      problem: 34,
      level: "B1",
    },
    Speaking: {
      answer: 10,
      problem: 10,
      level: "C1",
    },
    Reading: {
      answer: 8,
      problem: 34,
      level: "A1",
    },
    Grammar: {
      answer: 6,
      problem: 10,
      level: "B1",
    },
  });

  return (
    <Main ref={printRef}>
      <Wrapper>
        <ResultHeader />
        <Result1Privacy />
        <Content>
          <Result2DetailEvaluation result={result} />
          <ResultGraph>
            <Name>3. 결과 그래프</Name>
            <GraphDisplay>
              <Result3RadarChart result={result} />
              <Result3HorizonGraph result={result} />
            </GraphDisplay>
          </ResultGraph>
          <CEFR>
            <Name>4. CEFR 그래프</Name>
            <CEFRDisplay>
              <Result4Graph result={result} />
              <Result4CEFRExplain />
            </CEFRDisplay>
          </CEFR>
        </Content>
      </Wrapper>
      <Print>
        <ReactToPrint
          trigger={() => <PrintBtn>인쇄</PrintBtn>}
          content={() => printRef.current}
          documentTitle="제이케이에듀 레벨테스트"
        />
      </Print>
    </Main>
  );
};

export default Result;
